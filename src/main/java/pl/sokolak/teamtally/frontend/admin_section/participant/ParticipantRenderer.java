package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class ParticipantRenderer {

    private final List<TeamDto> teams;
    private final ParticipantService participantService;
    private final SessionService sessionService;

    ComponentRenderer<VerticalLayout, ParticipantDto> create() {
        return new ComponentRenderer<>(participant ->
        {
            UserDto user = participant.getUser();
            Component username = user.getUsername() != null
                    ? createNameItem(user)
                    : new Span("User unregistered");
            TeamComboBox teamComboBox = new TeamComboBox(participant, sessionService.getEvent(), teams, createComboBoxValueChangeListener());
            if (!participant.isActive()) {
                teamComboBox.setReadOnly(true);
            }
            VerticalLayout verticalLayout = new VerticalLayout(
                    new Span(username),
                    new Span(user.getEmail()),
                    teamComboBox);
            verticalLayout.addClassName("participants-grid-row");
            return verticalLayout;
        }
        );
    }

    private Component createNameItem(UserDto user) {
        String username = Optional.ofNullable(user.getUsername()).orElse("-");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String nameSeparator = firstName == null || lastName == null ? "" : " ";
        if (firstName == null && lastName == null) {
            return new Span(username);
        }
        return new Span(username + " (" + user.getFirstName() + nameSeparator + user.getLastName() + ")");
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> createComboBoxValueChangeListener() {
        return event -> {
            ParticipantDto participant = getParticipantForComboBox(event);
            TeamDto team = event.getValue();
            participant.setTeam(team);
            participantService.save(participant);
        };
    }

    private static ParticipantDto getParticipantForComboBox(AbstractField.ComponentValueChangeEvent<ComboBox<TeamDto>, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(TeamComboBox.class::cast)
                .map(TeamComboBox::getParticipant)
                .orElse(null);
    }

    @Getter
    private static class TeamComboBox extends ComboBox<TeamDto> {
        private final ParticipantDto participant;

        public TeamComboBox(ParticipantDto participant, EventDto event, List<TeamDto> teams,
                            ValueChangeListener<ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> changeValueListener) {
            super("", teams);
            this.participant = participant;
            this.setValue(participant.getTeam());
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-combobox");
            this.setPlaceholder("Select a team");
        }
    }
}
