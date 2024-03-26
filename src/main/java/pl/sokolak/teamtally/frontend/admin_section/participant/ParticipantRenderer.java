package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@AllArgsConstructor
class ParticipantRenderer {

    private final List<TeamDto> teams;
    private final ParticipantService participantService;
    private final SessionService sessionService;

    ComponentRenderer<VerticalLayout, UserDto> create() {
        return new ComponentRenderer<>(user ->
        {
            Span username = user.getUsername() != null
                    ? new Span(user.getUsername() + " (" + user.getFirstName() + " " + user.getLastName() + ")")
                    : new Span("User unregistered");
            VerticalLayout verticalLayout = new VerticalLayout(
                    new Span(user.getEmail()),
                    username,
                    new TeamComboBox(user, sessionService.getEvent(), teams, createComboBoxValueChangeListener())
            );
            verticalLayout.addClassName("participants-grid-row");
            return verticalLayout;
        }
        );
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> createComboBoxValueChangeListener() {
        return event -> {
            UserDto user = getUserForComboBox(event);
            TeamDto team = event.getValue();

            if (user == null || team == null) {
                return;
            }

            user.getParticipantForEvent(sessionService.getEvent())
                    .map(Data::getId)
                    .map(participantService::findById)
                    .flatMap(Function.identity())
                    .ifPresent(existing -> {
                        existing.setTeam(team);
                        participantService.save(existing);
                    });
        };
    }

    private static UserDto getUserForComboBox(AbstractField.ComponentValueChangeEvent<ComboBox<TeamDto>, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(TeamComboBox.class::cast)
                .map(TeamComboBox::getUser)
                .orElse(null);
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
            this.setPlaceholder("Select a team");
        }
    }
}
