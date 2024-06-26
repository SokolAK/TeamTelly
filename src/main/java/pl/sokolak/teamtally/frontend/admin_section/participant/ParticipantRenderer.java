package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.util.ImageUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class ParticipantRenderer {

    private final Set<TeamDto> teams;
    private final ParticipantService participantService;
    private final SessionService sessionService;

    public ParticipantRenderer(Set<TeamDto> teams, ParticipantService participantService, SessionService sessionService) {
        this.teams = new HashSet<>(teams);
        this.participantService = participantService;
        this.sessionService = sessionService;
    }

    ComponentRenderer<HorizontalLayout, ParticipantDto> create() {
        return new ComponentRenderer<>(participant -> {
            UserDto user = participant.getUser();
            Component username = user.getUsername() != null
                    ? createNameItem(user)
                    : new Span("User unregistered");
            TeamComboBox teamComboBox = new TeamComboBox(participant, teams, createComboBoxValueChangeListener());
            if (!participant.isActive()) {
                teamComboBox.setReadOnly(true);
            }
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Div photoContainer = new Div();
            photoContainer.addClassName("user-photo-div");
            Image photo = ImageUtil.createUserPhotoAsImageMedium(user.getPhoto());
            photo.addClassName("user-photo-medium");
            photoContainer.add(photo);
            VerticalLayout data = new VerticalLayout(
                    new Span(username),
                    new Span(user.getJobTitle()),
                    teamComboBox);
            data.addClassName("participants-grid-row");
            horizontalLayout.add(photoContainer, data);
            return horizontalLayout;
        }
        );
    }

    private Component createNameItem(UserDto user) {
        return new Html("<b>" + user.getFirstName() + " " + user.getLastName() + "</b>");
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> createComboBoxValueChangeListener() {
        return event -> {
            ParticipantDto participant = getParticipantForComboBox(event);
            TeamDto team = event.getValue();
            if(team == null) {
                participantService.updateTeam(participant, null);
                return;
            }
            if(team.getParticipants() == null) {
                team.setParticipants(new HashSet<>());
            }
            team.getParticipants().add(participant);
            participant.setTeam(team);
            participantService.updateTeam(participant, team);
            if (participant.equals(sessionService.getParticipant())) {
                sessionService.init();
            }
            if(team.getId().equals(sessionService.getParticipant().getTeamId())) {
                sessionService.getParticipant().setTeam(team);
            }
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

        public TeamComboBox(ParticipantDto participant, Set<TeamDto> teams,
                            ValueChangeListener<ComponentValueChangeEvent<ComboBox<TeamDto>, TeamDto>> changeValueListener) {
            super("", teams);
            setClearButtonVisible(true);
            this.participant = participant;
            this.setValue(participant.getTeam());
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-combobox");
            this.setPlaceholder("Select a team");
        }
    }
}
