package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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

    protected Grid<UserDto> grid;
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
        add(grid);
    }

    private void configureGrid() {
        List<UserDto> users = userService.findAll();
        List<ParticipantDto> participants = participantService.findAllByEvent(sessionService.getEvent());
        List<TeamDto> teams = teamService.findAllByEvent(sessionService.getEvent());

        grid = new Grid<>(UserDto.class);
        grid.addClassNames("participant-grid");
        grid.setAllRowsVisible(true);
        grid.setColumns();
        grid.addColumn(new CheckboxRenderer(
                participants, this::activateParticipant, this::deactivateParticipant
        ).create()).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(new ComboBoxRenderer(
                teams, participantService, sessionService
        ).create()).setAutoWidth(true);
        grid.setItems(users);
    }

    private void activateParticipant(UserDto user) {
        user.getParticipantForEvent(sessionService.getEvent())
                .ifPresentOrElse(
                        p -> changeActiveStatusOfExistingParticipant(p, true),
                        () -> participantService.save(createNewParticipant(user))
                );
    }

    private void changeActiveStatusOfExistingParticipant(ParticipantDto participant, boolean active) {
        participantService.findById(participant.getId())
                .ifPresent(existing -> {
                    existing.setActive(active);
                    participantService.save(existing);
                });
    }

    private ParticipantDto createNewParticipant(UserDto user) {
        return ParticipantDto.builder()
                .event(sessionService.getEvent())
                .user(user)
                .active(true)
                .build();
    }

    private void deactivateParticipant(UserDto user) {
        user.getParticipantForEvent(sessionService.getEvent())
                .ifPresent(p -> changeActiveStatusOfExistingParticipant(p, false));
    }
}
