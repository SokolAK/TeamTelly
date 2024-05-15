package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantRepository;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.*;
import java.util.stream.Collectors;

@SpringComponent(value = "participant-view-admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/participant", layout = MainView.class)
@PageTitle("Participants")
public class ParticipantView extends AbstractViewWithSideForm<ParticipantDto> {

    protected UserService userService;
    protected TeamService teamService;
    protected SessionService sessionService;
    private Set<TeamDto> teams;
    private EventDto event;

    public ParticipantView(UserService userService,
                           ParticipantService participantService,
                           TeamService teamService,
                           SessionService sessionService) {
        this.service = participantService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.teamService = teamService;
        this.form = new ParticipantForm(sessionService.getEvent(), userService);
        init();
    }

    @Override
    protected void configureGrid() {
        event = sessionService.getEvent();
        teams = fetchTeams(event);
//        participants = fetchParticipants(event, teams);

        grid = new Grid<>(ParticipantDto.class);
        grid.addClassNames("participant-grid");
        grid.setAllRowsVisible(true);
        grid.setColumns();
//        grid.addColumn(new ActiveCheckboxRenderer(
//                event, this::activateParticipant, this::deactivateParticipant
//        ).create()).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(new ParticipantRenderer(teams, (ParticipantService) service, sessionService).create()).setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, participant) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.removeParticipant(participant));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setAutoWidth(true).setFlexGrow(0);
    }

    private Set<ParticipantDto> fetchParticipants(EventDto event, Set<TeamDto> teams) {
        return ((ParticipantService) service).findAllActiveDataByEvent(event).stream()
                .map(p -> ParticipantDto.builder()
                        .id(p.getId())
                        .active(true)
                        .user(UserDto.builder()
                                .username(p.getUsername())
                                .firstName(p.getFirstName())
                                .lastName(p.getLastName())
                                .jobTitle(p.getJobTitle())
                                .photo(p.getPhoto())
                                .build())
                        .team(teams.stream()
                                .filter(t -> t.getId().equals(p.getTeamId()))
                                .findFirst()
                                .orElse(null))
                        .build()
                ).collect(Collectors.toSet());
    }

    private Set<TeamDto> fetchTeams(EventDto event) {
        return teamService.findAllDataViewByEvent(event).stream()
                .map(t -> TeamDto.builder()
                        .id(t.getId())
                        .icon(t.getIcon())
                        .name(t.getName())
                        .color(t.getColor())
                        .build())
                .collect(Collectors.toSet());
    }

    @Override
    protected void addData() {
        form.init();
        form.setVisible(true);
    }

    @Override
    protected ParticipantDto emptyData() {
        return ParticipantDto.builder().build();
    }

    @Override
    protected void saveOrUpdateData(SaveEvent event) {
        Optional.ofNullable(event.getData())
                .map(ParticipantForm.ParticipantsToAdd.class::cast)
                .ifPresent(participant -> {
                    saveParticipantsFromUsers(participant.users());
                    saveParticipantsFromEmails(participant.emails());
                });
        updateList();
        closeEditor();
    }

    private void saveParticipantsFromUsers(Set<UserDto> users) {
        users.stream()
                .map(this::createNewParticipant)
                .forEach(p -> service.save(p));
    }

    private void saveParticipantsFromEmails(Set<String> emails) {
        emails.stream()
                .map(email -> userService.findFirstByEmail(email))
                .flatMap(Optional::stream)
                .map(this::createNewParticipant)
                .forEach(p -> service.save(p));
        emails.stream()
                .filter(email -> userService.findFirstByEmail(email).isEmpty())
                .map(this::createNewParticipant)
                .forEach(p -> service.save(p));
    }

    private ParticipantDto createNewParticipant(UserDto user) {
        return ParticipantDto.builder()
                .active(true)
                .event(sessionService.getEvent())
                .user(user)
                .build();
    }

    private ParticipantDto createNewParticipant(String email) {
        return ParticipantDto.builder()
                .active(false)
                .event(sessionService.getEvent())
                .user(UserDto.builder()
                        .username("<Not signed in>")
                        .email(email)
                        .build())
                .build();
    }

    private void removeParticipant(ParticipantDto participant) {
        service.delete(participant);
        updateList();
    }

    @Override
    protected List<ParticipantDto> fetchData() {
        return new ArrayList<>(fetchParticipants(event, teams));
    }

    @Override
    protected Comparator<ParticipantDto> getComparator() {
        return Comparator.comparing(p -> p.getUser().getUsername());
    }
}
