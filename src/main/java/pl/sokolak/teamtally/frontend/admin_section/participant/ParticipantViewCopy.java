//package pl.sokolak.teamtally.frontend.admin_section.participant;
//
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//import pl.sokolak.teamtally.backend.event.EventDto;
//import pl.sokolak.teamtally.backend.participant.ParticipantDto;
//import pl.sokolak.teamtally.backend.participant.ParticipantService;
//import pl.sokolak.teamtally.backend.session.SessionService;
//import pl.sokolak.teamtally.backend.team.TeamDto;
//import pl.sokolak.teamtally.backend.team.TeamService;
//import pl.sokolak.teamtally.backend.user.UserDto;
//import pl.sokolak.teamtally.backend.user.UserService;
//import pl.sokolak.teamtally.frontend.MainView;
//import pl.sokolak.teamtally.frontend.common.AbstractForm;
//
//import java.util.List;
//
//@SpringComponent(value = "participant-view-admin")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@RolesAllowed("ADMIN")
//@Route(value = "/admin/participant", layout = MainView.class)
//@PageTitle("Participants")
//public class ParticipantViewCopy extends VerticalLayout {
//
//    protected Grid<UserDto> grid;
//    protected UserService userService;
//    protected ParticipantService participantService;
//    protected TeamService teamService;
//    protected AbstractForm form;
//    protected SessionService sessionService;
//
//    public ParticipantViewCopy(UserService userService,
//                               ParticipantService participantService,
//                               TeamService teamService,
//                               SessionService sessionService) {
//        this.sessionService = sessionService;
//        this.userService = userService;
//        this.participantService = participantService;
//        this.teamService = teamService;
//        configureGrid();
//        add(grid);
//    }
//
//    private void configureGrid() {
//        EventDto event = sessionService.getEvent();
//        List<UserDto> users = userService.findAll();
//        List<TeamDto> teams = teamService.findAllByEvent(event);
//
//        grid = new Grid<>(UserDto.class);
//        grid.addClassNames("participant-grid");
//        grid.setAllRowsVisible(true);
//        grid.setColumns();
//        grid.addColumn(new ActiveCheckboxRenderer(
//                event, this::activateParticipant, this::deactivateParticipant
//        ).create()).setAutoWidth(true).setFlexGrow(0);
//        grid.addColumn(new ParticipantRenderer(
//                teams, participantService, sessionService
//        ).create()).setAutoWidth(true);
//        grid.setItems(users);
//    }
//
//    private void activateParticipant(UserDto user) {
//        user.getParticipantForEvent(sessionService.getEvent())
//                .ifPresentOrElse(
//                        participant -> {
//                            ParticipantDto saved = changeActiveStatusOfExistingParticipant(participant, true);
//                            user.getParticipants().remove(saved);
//                            user.getParticipants().add(saved);
//                        },
//                        () -> {
//                            ParticipantDto saved = participantService.save(createNewParticipant(user));
//                            user.getParticipants().add(saved);
//                        }
//                );
//        grid.getDataProvider().refreshItem(user);
//    }
//
//    private ParticipantDto changeActiveStatusOfExistingParticipant(ParticipantDto participant, boolean active) {
//        return participantService.findById(participant.getId())
//                .map(existing -> {
//                    existing.setActive(active);
//                    return participantService.save(existing);
//                }).orElse(participant);
//    }
//
//    private ParticipantDto createNewParticipant(UserDto user) {
//        return ParticipantDto.builder()
//                .event(sessionService.getEvent())
//                .user(user)
//                .active(true)
//                .build();
//    }
//
//    private void deactivateParticipant(UserDto user) {
//        user.getParticipantForEvent(sessionService.getEvent())
//                .ifPresent(p -> p.setActive(
//                        changeActiveStatusOfExistingParticipant(p, false).isActive()
//                ));
//        grid.getDataProvider().refreshItem(user);
//    }
//}
