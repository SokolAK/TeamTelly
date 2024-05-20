package pl.sokolak.teamtally.frontend.user_section.challenge;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.admin_section.challenge.ChallengeRenderer;
import pl.sokolak.teamtally.frontend.common.AbstractView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.*;
import java.util.stream.Collectors;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/challenge", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends AbstractView<ChallengeDto> {

    private final SessionService sessionService;
    private final ParticipantService participantService;
    private final CodeService codeService;
    private final TextField codeField = new TextField();

    public ChallengeView(ChallengeService service,
                         CodeService codeService,
                         ParticipantService participantService,
                         SessionService sessionService,
                         PointsCalculator pointsCalculator) {
        this.service = service;
        this.codeService = codeService;
        this.participantService = participantService;
        this.sessionService = sessionService;
        addClassName("challenge-view");
        init();
    }

    @Override
    protected Component getToolbar() {
        VerticalLayout toolbar = new VerticalLayout();
        codeField.setPlaceholder("Insert code");
        Button confirmButton = new Button("Submit", new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));
        confirmButton.addClickListener(confirmButtonListener());
//        toolbar.setFlexGrow(1, codeField);
        toolbar.setWidthFull();
        toolbar.add(new HorizontalLayout(codeField, confirmButton));
        return toolbar;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class, false);
        grid.addClassNames("challenge-grid");
        grid.setSizeFull();
        populateGrid();
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.ASCENDING)));
    }

    private List<Integer> getCompletedIndividualChallenges() {
        return Optional.ofNullable(sessionService.getParticipant())
                .map(ParticipantDto::getCompletedChallenges)
                .orElse(Collections.emptySet())
                .stream()
                .map(Data::getId)
                .collect(Collectors.toList());
    }

    private List<Integer> getCompletedTeamChallenges() {
        TeamDto team = sessionService.getParticipant().getTeam();
        Set<ChallengeDto> challenges = Optional.ofNullable(team)
                .map(TeamDto::getParticipants)
                .orElse(Collections.emptySet())
                .stream().filter(ParticipantDto::isActive)
                .map(ParticipantDto::getCompletedChallenges)
                .min(Comparator.comparingInt(Set::size))
                .orElse(Collections.emptySet());
        return challenges.stream()
                .filter(c -> team.getParticipants().stream()
                        .map(ParticipantDto::getCompletedChallenges)
                        .allMatch(cs -> cs.contains(c)))
                .map(ChallengeDto::getId)
                .collect(Collectors.toList());
    }

    @Override
    protected List<ChallengeDto> fetchData() {
        return new ArrayList<>(((ChallengeService) service).findAllDataByEvent(sessionService.getEvent()));
    }

    private ComponentEventListener<ClickEvent<Button>> confirmButtonListener() {
        return buttonClickEvent -> {
            String insertedCode = codeField.getValue();
            if (isEmpty(insertedCode)) {
                NotificationService.showWarning("Please insert code");
                return;
            }
            List<CodeDto> codes = codeService.findAllByEvent(sessionService.getEvent());
            codes.stream()
                    .filter(c -> c.getCode().equals(insertedCode))
                    .findFirst()
                    .ifPresentOrElse(
                            this::tryCompleteChallenge,
                            () -> NotificationService.showWarning("Wrong code")
                    );
        };
    }

    private void tryCompleteChallenge(CodeDto code) {
        if (!code.canBeUsed()) {
            NotificationService.showWarning("Code already used");
            return;
        }

        ParticipantDto participant = sessionService.getParticipant();
        if(!didParticipantCompleteChallenge(participant, code)) {
            participant.completeChallenge(code);
            participantService.save(participant);
            code.setEvent(sessionService.getEvent());
            code.use();
            codeService.save(code);
            NotificationService.showSuccess("Hurray! You got " + code.getChallenge().getIndividualPoints() + " points for " + code.getChallenge().getName());
            populateGrid();
        } else {
            NotificationService.showWarning("You already completed " + code.getChallenge().getName());
        }
    }

    private void populateGrid() {
        grid.setColumns();
        grid.addColumn(ChallengeRenderer.create(
                getCompletedIndividualChallenges(), getCompletedTeamChallenges()
        ));
    }

    private boolean isEmpty(String code) {
        return code == null || code.isEmpty();
    }

    private boolean didParticipantCompleteChallenge(ParticipantDto participant, CodeDto code) {
        return participant.getCompletedChallenges().contains(code.getChallenge());
    }
}
