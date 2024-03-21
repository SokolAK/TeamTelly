package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.Data;
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
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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
        grid.setColumns();
        grid.addColumn(createCheckboxRenderer(participants)).setAutoWidth(true).setFlexGrow(0);
//        grid.addColumn(ParticipantRenderer.create()).setAutoWidth(true);
        grid.addColumn(createTeamRenderer(teams)).setAutoWidth(true);
        grid.setAllRowsVisible(true);
        grid.setItems(users);
    }

    private void activateParticipant(UserDto user, EventDto event) {
        user.getParticipantForEvent(event)
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

    private void deactivateParticipant(UserDto user, EventDto event) {
        user.getParticipantForEvent(event)
                .ifPresent(p -> changeActiveStatusOfExistingParticipant(p, false));
    }

    private ComponentRenderer<CheckboxWithUser, UserDto> createCheckboxRenderer(List<ParticipantDto> participants) {
        return new ComponentRenderer<>(user ->
                new CheckboxWithUser(user, isParticipant(user, participants), createCheckboxValueChangeListener()));
    }

    private ComponentRenderer<VerticalLayout, UserDto> createTeamRenderer(List<TeamDto> teams) {
        return new ComponentRenderer<>(user ->
        {
            VerticalLayout verticalLayout = new VerticalLayout(
                    new Span(user.getUsername() + " (" + user.getFirstName() + " " + user.getLastName() + ")"),
                    new Span(user.getEmail()),
                    new TeamComboBox(user, sessionService.getEvent(), teams, createComboBoxValueChangeListener())
            );
            verticalLayout.addClassName("participants-grid-row");
            return verticalLayout;
        }
        );
    }

    private static boolean isParticipant(UserDto user, List<ParticipantDto> participants) {
        return participants
                .stream()
                .filter(ParticipantDto::isActive)
                .map(ParticipantDto::getUser)
                .map(UserDto::getId)
                .anyMatch(id -> id.equals(user.getId()));
    }

    private ValueChangeListener<ComponentValueChangeEvent<Checkbox, Boolean>> createCheckboxValueChangeListener() {
        return event -> {
            UserDto user = getUserForCheckbox(event);
            Boolean isChecked = event.getValue();

            if (user == null || isChecked == null) {
                return;
            }

            if (isChecked) {
                activateParticipant(user, sessionService.getEvent());
            } else {
                deactivateParticipant(user, sessionService.getEvent());
            }
        };
    }

    private ValueChangeListener<ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> createComboBoxValueChangeListener() {
        return event -> {
            UserDto user = getUserForComboBox(event);
            TeamDto team = event.getValue();

            if (user == null || team == null) {
                return;
            }

            user.getParticipantForEvent(sessionService.getEvent())
                    .map(Data::getId)
                    .map(id -> participantService.findById(id))
                    .flatMap(Function.identity())
                    .ifPresent(existing -> {
                        existing.setTeam(team);
                        participantService.save(existing);
                    });
        };
    }

    private static UserDto getUserForCheckbox(ComponentValueChangeEvent<Checkbox, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(CheckboxWithUser.class::cast)
                .map(CheckboxWithUser::getUser)
                .orElse(null);
    }

    private static UserDto getUserForComboBox(ComponentValueChangeEvent<ComboBox<TeamDto>, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(TeamComboBox.class::cast)
                .map(TeamComboBox::getUser)
                .orElse(null);
    }

    @Getter
    private static class CheckboxWithUser extends Checkbox {
        private final UserDto user;

        public CheckboxWithUser(UserDto user, boolean initialValue,
                                ValueChangeListener<ComponentValueChangeEvent<Checkbox, Boolean>> changeValueListener) {
            super(initialValue);
            this.user = user;
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-checkbox");
        }
    }

    @Getter
    private static class TeamComboBox extends ComboBox<TeamDto> {
        private final UserDto user;

        public TeamComboBox(UserDto user, EventDto event, List<TeamDto> teams,
                            ValueChangeListener<ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> changeValueListener) {
            super("", teams);
            this.user = user;
            this.setValue(Optional.ofNullable(user)
                    .map(UserDto::getParticipants)
                    .orElse(Set.of())
                    .stream()
                    .filter(ParticipantDto::isActive)
                    .filter(p -> p.getEvent().equals(event))
                    .findFirst()
                    .map(ParticipantDto::getTeam)
                    .orElse(null));
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-combobox");
            this.setPlaceholder("Select team");
        }
    }
}
