package pl.sokolak.teamtally.frontend.user_section.challenge;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.history.HistoryService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.util.EventBus;
import pl.sokolak.teamtally.backend.util.LogService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.admin_section.challenge.ChallengeRenderer;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/challenge", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends VerticalLayout {

    private final ChallengeService challengeService;
    private final ParticipantService participantService;
    private final CodeService codeService;
    private final SessionService sessionService;
    private final HistoryService historyService;
    private final LogService log;
    private final EventBus eventBus;

    private final TextField codeField = new TextField();
    private final Grid<ChallengeDto> challengesGrid = new Grid<>(ChallengeDto.class, false);
    private final Grid<ChallengeDto> completedChallengesGrid = new Grid<>(ChallengeDto.class, false);

    public ChallengeView(
            ChallengeService challengeService,
            ParticipantService participantService,
            CodeService codeService,
            SessionService sessionService,
            HistoryService historyService,
            LogService logService,
            EventBus eventBus) {
        this.challengeService = challengeService;
        this.participantService = participantService;
        this.codeService = codeService;
        this.sessionService = sessionService;
        this.historyService = historyService;
        this.log = logService;
        this.eventBus = eventBus;
        addClassName("challenge-view");

        configureGrids();
        populateGrids();
        setSpacing(false);
        challengesGrid.getStyle().set("margin-bottom", "0").set("margin-top", "10px");
        add(createCodeSection(), challengesGrid, completedChallengesGrid);
    }

    private void configureGrids() {
        challengesGrid.addClassNames("challenge-grid");
        challengesGrid.setAllRowsVisible(true);
        completedChallengesGrid.addClassNames("challenge-grid");
        completedChallengesGrid.setAllRowsVisible(true);
    }

    private Component createCodeSection() {
        codeField.setPlaceholder("Insert code");

        Button submitButton = new Button("Submit", new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));
//        submitButton.addClassName("shadow");
        submitButton.addClickShortcut(Key.ENTER);
        submitButton.addClickListener(submitButtonListener());
//        toolbar.setFlexGrow(1, codeField);

        HorizontalLayout toolbar = new HorizontalLayout(codeField, submitButton);
        toolbar.setWidthFull();
        toolbar.setMaxWidth("600px");
        toolbar.setFlexGrow(1, codeField);
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return toolbar;
    }

    private ComponentEventListener<ClickEvent<Button>> submitButtonListener() {
        return buttonClickEvent -> {
            historyService.save(sessionService.getUser(), "submit code " + codeField.getValue());
            log.info("Submit button clicked");
            String insertedCode = codeField.getValue();
            if (isEmpty(insertedCode)) {
                NotificationService.showWarning("Please insert code");
                log.info("Code field is empty");
                return;
            }
            List<CodeDto> codes = codeService.findAllByEvent(sessionService.getEvent());
            codes.stream()
                    .filter(c -> c.getCode().equals(insertedCode))
                    .findFirst()
                    .ifPresentOrElse(
                            this::tryCompleteChallenge,
                            this::handleWrongCode
                    );
            codeField.clear();
        };
    }

    private void handleWrongCode() {
        NotificationService.showWarning("Wrong code");
        log.info("Wrong code inserted [{}]", codeField.getValue());
    }

    private void tryCompleteChallenge(CodeDto code) {
        if (!code.canBeUsed()) {
            NotificationService.showWarning("Code already used");
            log.info("Submitted code already used [{}]", codeField.getValue());
            return;
        }

        ParticipantDto participant = sessionService.getParticipant();
        if (!didParticipantCompleteChallenge(participant, code)) {
            participantService.updateCodeAndChallenge(participant, code, code.getChallenge());
            participant.completeChallenge(code);
            code.setEvent(sessionService.getEvent());
            code.use();
            codeService.save(code);
            eventBus.push("my-points", new PointsCalculator().calculate(sessionService.getParticipant()));

            NotificationService.showSuccess("Hurray! You got " +
                                            code.getChallenge().getIndividualPoints() + " points for " + code.getChallenge().getName());
            log.info("Code used [{}]", codeField.getValue());
            log.info("Got {} points for '{}' [{}]",
                    code.getChallenge().getIndividualPoints(), code.getChallenge().getName(), codeField.getValue());

            populateGrids();
        } else {
            NotificationService.showWarning("You already completed " + code.getChallenge().getName());
        }
    }

    private void populateGrids() {
        long start = System.currentTimeMillis();
        Set<ChallengeDto> challenges = getChallengesForEvent();
        long end = System.currentTimeMillis();
        System.out.println("Getting challenges for event " + (end - start));
        start = end;
        Set<Integer> completedIndividualChallengesIds = getCompletedIndividualChallenges();
        end = System.currentTimeMillis();
        System.out.println("Getting completed individual challenges " + (end - start));
        start = end;
        Set<Integer> completedTeamChallengesIds = getCompletedTeamChallenges();
        end = System.currentTimeMillis();
        System.out.println("Getting completed by team challenges " + (end - start));

        Set<ChallengeDto> uncompletedIndividualChallenges = challenges.stream()
                .filter(c -> !completedIndividualChallengesIds.contains(c.getId()))
                .collect(Collectors.toSet());

        challengesGrid.setItems(uncompletedIndividualChallenges.stream()
                .sorted(Comparator.comparing(ChallengeDto::getName))
                .collect(Collectors.toList()));
        challengesGrid.setColumns();
        challengesGrid.addColumn(ChallengeRenderer.createChallengesRenderer(completedTeamChallengesIds));

        if (completedIndividualChallengesIds.isEmpty()) {
            completedChallengesGrid.setItems(Set.of());
            completedChallengesGrid.setColumns();
            completedChallengesGrid.setVisible(false);
        } else {
            Set<ChallengeDto> completedIndividualChallenges = challenges.stream()
                    .filter(c -> completedIndividualChallengesIds.contains(c.getId()))
                    .collect(Collectors.toSet());

            completedChallengesGrid.setItems(completedIndividualChallenges.stream()
                    .sorted(Comparator.comparing(ChallengeDto::getName))
                    .collect(Collectors.toList()));
            completedChallengesGrid.setColumns();
            completedChallengesGrid.addColumn(ChallengeRenderer.createCompletedChallengesRenderer(completedTeamChallengesIds));
            completedChallengesGrid.setVisible(true);
        }
    }

    private Set<ChallengeDto> getChallengesForEvent() {
        return challengeService.findAllDataByEvent(sessionService.getEvent());
    }

    private Set<Integer> getCompletedIndividualChallenges() {
        return challengeService.findAllIdsCompletedByParticipant(sessionService.getParticipant());
    }

    private Set<Integer> getCompletedTeamChallenges() {
        if (sessionService.getParticipant().getTeam() == null) {
            return Set.of();
        }
        return challengeService.findAllIdsCompletedByTeam(sessionService.getParticipant().getTeam());
    }

    private boolean isEmpty(String code) {
        return code == null || code.isEmpty();
    }

    private boolean didParticipantCompleteChallenge(ParticipantDto participant, CodeDto code) {
        return participant.getCompletedChallenges().contains(code.getChallenge());
    }


//
//    private final SessionService sessionService;
//    private final ParticipantService participantService;
//    private final ChallengeService challengeService;
//    private final CodeService codeService;
//    private final TextField codeField = new TextField();
//    private final EventBus eventBus;
//    private final LogService log;
//
//    public ChallengeView(ChallengeService service,
//                         CodeService codeService,
//                         ParticipantService participantService,
//                         ChallengeService challengeService,
//                         SessionService sessionService,
//                         EventBus eventBus,
//                         LogService log) {
//        this.service = service;
//        this.codeService = codeService;
//        this.participantService = participantService;
//        this.challengeService = challengeService;
//        this.sessionService = sessionService;
//        this.eventBus = eventBus;
//        this.log = log;
//        log.info("Show challenge view");
//        addClassName("challenge-view");
//        init();
//    }
//
//    @Override
//    protected Component getToolbar() {
//        codeField.setPlaceholder("Insert code");
//
//        Button submitButton = new Button("Submit", new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));
////        submitButton.addClassName("shadow");
//        submitButton.addClickShortcut(Key.ENTER);
//        submitButton.addClickListener(submitButtonListener());
////        toolbar.setFlexGrow(1, codeField);
//
//        HorizontalLayout toolbar = new HorizontalLayout(codeField, submitButton);
//        toolbar.setWidthFull();
//        toolbar.setMaxWidth("600px");
//        toolbar.setFlexGrow(1, codeField);
//        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
//        return toolbar;
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(ChallengeDto.class, false);
//        grid.addClassNames("challenge-grid");
//        grid.setAllRowsVisible(true);
//        populateGrid();
//        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.ASCENDING)));
//    }
//
//    private Set<Integer> getCompletedIndividualChallenges() {
//        return challengeService.findAllIdsCompletedByParticipant(sessionService.getParticipant());
//    }
//
//    private Set<Integer> getCompletedTeamChallenges() {
//        if(sessionService.getParticipant().getTeam() == null) {
//            return Set.of();
//        }
//        return challengeService.findAllIdsCompletedByTeam(sessionService.getParticipant().getTeam());
//    }
//
//    @Override
//    protected List<ChallengeDto> fetchData() {
//        return new ArrayList<>(((ChallengeService) service).findAllDataByEvent(sessionService.getEvent()));
//    }
//
//    @Override
//    protected Comparator<ChallengeDto> getComparator() {
//        Set<Integer> allIdsCompletedByParticipant = challengeService.findAllIdsCompletedByParticipant(sessionService.getParticipant());
//        return Comparator.comparing((c1, c2) -> ((ChallengeDto) c1).c).thenComparing(ChallengeDto::getName);
//    }
//
//    private ComponentEventListener<ClickEvent<Button>> submitButtonListener() {
//        return buttonClickEvent -> {
//            log.info("Submit button clicked");
//            String insertedCode = codeField.getValue();
//            if (isEmpty(insertedCode)) {
//                NotificationService.showWarning("Please insert code");
//                log.info("Code field is empty");
//                return;
//            }
//            List<CodeDto> codes = codeService.findAllByEvent(sessionService.getEvent());
//            codes.stream()
//                    .filter(c -> c.getCode().equals(insertedCode))
//                    .findFirst()
//                    .ifPresentOrElse(
//                            this::tryCompleteChallenge,
//                            this::handleWrongCode
//                    );
//            codeField.clear();
//        };
//    }
//
//    private void handleWrongCode() {
//        NotificationService.showWarning("Wrong code");
//        log.info("Wrong code inserted [{}]", codeField.getValue());
//    }
//
//    private void tryCompleteChallenge(CodeDto code) {
//        if (!code.canBeUsed()) {
//            NotificationService.showWarning("Code already used");
//            log.info("Submitted code already used [{}]", codeField.getValue());
//            return;
//        }
//
//        ParticipantDto participant = sessionService.getParticipant();
//        if (!didParticipantCompleteChallenge(participant, code)) {
//            participantService.updateCodeAndChallenge(participant, code, code.getChallenge());
//            participant.completeChallenge(code);
//            code.setEvent(sessionService.getEvent());
//            code.use();
//            codeService.save(code);
//            eventBus.push("my-points", new PointsCalculator().calculate(sessionService.getParticipant()));
//
//            NotificationService.showSuccess("Hurray! You got " +
//                    code.getChallenge().getIndividualPoints() + " points for " + code.getChallenge().getName());
//            log.info("Code used [{}]", codeField.getValue());
//            log.info("Got {} points for '{}' [{}]",
//                    code.getChallenge().getIndividualPoints(), code.getChallenge().getName(), codeField.getValue());
//
//            populateGrid();
//        } else {
//            NotificationService.showWarning("You already completed " + code.getChallenge().getName());
//        }
//    }
//
//    private void populateGrid() {
//        grid.setColumns();
//        grid.addColumn(ChallengeRenderer.create(
//                getCompletedIndividualChallenges(),
//                getCompletedTeamChallenges()
//        ));
//    }
//
//    private boolean isEmpty(String code) {
//        return code == null || code.isEmpty();
//    }
//
//    private boolean didParticipantCompleteChallenge(ParticipantDto participant, CodeDto code) {
//        return participant.getCompletedChallenges().contains(code.getChallenge());
//    }
}
