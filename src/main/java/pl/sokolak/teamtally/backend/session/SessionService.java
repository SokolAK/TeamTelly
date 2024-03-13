package pl.sokolak.teamtally.backend.session;

import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SessionService {

    private final SessionContext sessionContext;
    private final SecurityService securityService;
    private final ParticipantService participantService;

    public void init() {
        UserDto authenticatedUser = securityService.getAuthenticatedUser();
        List<ParticipantDto> participants = getParticipants(authenticatedUser);
        List<EventDto> participantsOngoingEvents = getOngoingEvents(participants);
        EventDto event = getFirst(participantsOngoingEvents);
        ParticipantDto participant = getParticipant(participants, event);

        sessionContext.setEvent(event);
        sessionContext.setParticipant(participant);
    }

    public UserDto getUser() {
        return securityService.getAuthenticatedUser();
    }

    public EventDto getEvent() {
        return sessionContext.getEvent();
    }

    public String getEventName() {
        return Optional.ofNullable(sessionContext)
                .map(SessionContext::getEvent)
                .map(EventDto::getName)
                .orElse(null);
    }

    public LocalDate getEventStartDate() {
        return Optional.ofNullable(sessionContext)
                .map(SessionContext::getEvent)
                .map(EventDto::getStartDate)
                .orElse(null);
    }

    public LocalDate getEventEndDate() {
        return Optional.ofNullable(sessionContext)
                .map(SessionContext::getEvent)
                .map(EventDto::getEndDate)
                .orElse(null);
    }

    private List<ParticipantDto> getParticipants(UserDto authenticatedUser) {
        return Optional.ofNullable(authenticatedUser)
                .map(participantService::findByUser)
                .orElse(Collections.emptyList());
    }

    private List<EventDto> getOngoingEvents(List<ParticipantDto> participants) {
        LocalDate now = LocalDate.now();
        return participants.stream()
                .map(ParticipantDto::getEvent)
                .filter(e -> e.getStartDate().isEqual(now) || e.getStartDate().isBefore(now))
                .filter(e -> e.getEndDate().isEqual(now) || e.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    private EventDto getFirst(List<EventDto> events) {
        return events.stream().min(Comparator.comparing(EventDto::getName)).orElse(null);
    }

    private static ParticipantDto getParticipant(List<ParticipantDto> participants, EventDto event) {
        return event == null ? null : participants.stream()
                .filter(p -> p.getEvent().getId().equals(event.getId()))
                .findFirst().orElse(null);
    }
}
