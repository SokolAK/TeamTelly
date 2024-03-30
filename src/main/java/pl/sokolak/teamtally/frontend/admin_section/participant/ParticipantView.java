package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

import java.util.List;

@SpringComponent(value = "participant-view-admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/participant", layout = MainView.class)
@PageTitle("Participants")
public class ParticipantView extends VerticalLayout {

    protected Grid<ParticipantDto> grid;
    protected UserService userService;
    protected ParticipantService participantService;
    protected TeamService teamService;
    protected AbstractForm form;
    protected SessionService sessionService;

    public ParticipantView(UserService userService,
                           ParticipantService participantService,
                           TeamService teamService,
                           SessionService sessionService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.participantService = participantService;
        this.teamService = teamService;
        configureGrid();
        add(getToolbar(), grid);
    }

    private Component getToolbar() {
        Button addDataButton = new Button("Add");
//        addDataButton.addClickListener(click -> addData());
        var toolbar = new HorizontalLayout(addDataButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        EventDto event = sessionService.getEvent();
        List<TeamDto> teams = teamService.findAllByEvent(event);
        List<ParticipantDto> participants = participantService.findAllByEvent(event);

        grid = new Grid<>(ParticipantDto.class);
        grid.addClassNames("participant-grid");
        grid.setAllRowsVisible(true);
        grid.setColumns();
        grid.addColumn(new ActiveCheckboxRenderer(
                event, this::activateParticipant, this::deactivateParticipant
        ).create()).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(new ParticipantRenderer(
                teams, participantService, sessionService
        ).create()).setAutoWidth(true);
        grid.setItems(participants);
    }

    private void activateParticipant(ParticipantDto participant) {
        changeParticipantActiveStatus(participant, true);
    }

    private void deactivateParticipant(ParticipantDto participant) {
        changeParticipantActiveStatus(participant, false);
    }

    private void changeParticipantActiveStatus(ParticipantDto participant, boolean active) {
        participant.setActive(active);
        participantService.save(participant);
        grid.getDataProvider().refreshItem(participant);
    }
}
