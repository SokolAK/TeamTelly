package pl.sokolak.teamtally.frontend.user_section.challenge;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.admin_section.challenge.ChallengeRenderer;
import pl.sokolak.teamtally.frontend.common.AbstractView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.List;

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
                         SessionService sessionService) {
        this.service = service;
        this.codeService = codeService;
        this.participantService = participantService;
        this.sessionService = sessionService;
        addClassName("challenge-view");
        init();
    }

    @Override
    protected void configureToolbar() {
        toolbar = new HorizontalLayout();
        codeField.setPlaceholder("Insert code");
        Button confirmButton = new Button(new Icon(VaadinIcon.STAR));
        confirmButton.addClickListener(confirmButtonListener());
        toolbar.setFlexGrow(1, codeField);
        toolbar.setWidthFull();
        toolbar.add(codeField, confirmButton);
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class, false);
        grid.addClassNames("challenge-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(ChallengeRenderer.create());
//        grid.addColumn(ChallengeRenderer.create(
//                        List.of(UUID.fromString("afb75070-8176-4ccc-81ed-b0ea786335e2"),
//                                UUID.fromString("3b7b5558-7319-4a7d-8882-8e5825bba707")),
//                        List.of(UUID.fromString("3b7b5558-7319-4a7d-8882-8e5825bba707"))))
//                .setHeader("Name")
//                .setComparator(ChallengeDto::getName);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.ASCENDING)));
    }

    @Override
    protected List<ChallengeDto> fetchData() {
        return ((ChallengeService) service).findAllByEvent(sessionService.getEvent());
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
        if(!code.isActive()) {
            NotificationService.showWarning("Code already used");
            return;
        }

        ParticipantDto participant = sessionService.getParticipant();
        participant.addCompletedChallenge(code.getChallenge());
        participantService.save(participant);
        code.setEvent(sessionService.getEvent());
        code.setActive(false);
        codeService.save(code);
        NotificationService.showSuccess("Hurray! You got " + code.getChallenge().getIndividualPoints());
    }

    private boolean isEmpty(String code) {
        return code == null || code.isEmpty();
    }
}
