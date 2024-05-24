package pl.sokolak.teamtally.backend.session;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class SessionService {

    private final SessionContext sessionContext;
    private final SecurityService securityService;
    private final ParticipantService participantService;
    private final EventService eventService;
    private final UserService userService;

    public void init() {
        UserDto authenticatedUser = securityService.getAuthenticatedUser();
        if(authenticatedUser == null) {
            securityService.logout();
            return;
        }

        if(!authenticatedUser.isLogged()) {
            authenticatedUser.setLogged(true);
            userService.updateLogged(authenticatedUser);
        }

        // TODO
//        List<ParticipantDto> participants = getParticipants(authenticatedUser);
//        List<EventDto> participantsEvents = getAllEvents(participants);
//        EventDto event = Optional.ofNullable(sessionContext.getEvent())
//                .filter(e -> participantsEvents.stream()
//                        .anyMatch(e::equals))
//                .orElseGet(() -> getLast(participantsEvents));
        Optional<EventDto> maybeEvent = eventService.findAllData().stream().findFirst();
        if(maybeEvent.isPresent()) {
            EventDto event = maybeEvent.get();
            sessionContext.setEvent(event);
            sessionContext.setEvents(List.of(event));
            sessionContext.setParticipant(participantService.findById(
                    authenticatedUser.getParticipants().stream()
                            .findFirst()
                            .map(Data::getId)
                            .orElse(null)));
        }

        log.info("[{}] Init session service", authenticatedUser.getUsername());
    }

    public void reinit(EventDto event) {
        sessionContext.setEvent(event);
        init();
    }

    public UserDto getUser() {
        return securityService.getAuthenticatedUser();
    }

    public List<EventDto> getEvents() {
        return sessionContext.getEvents();
    }

    public EventDto getEvent() {
        return sessionContext.getEvent();
    }

    public boolean hasEvent() {
        return sessionContext.getEvent() != null;
    }

    public ParticipantDto getParticipant() {
        return sessionContext.getParticipant();
    }

    public String getEventName() {
        return Optional.ofNullable(sessionContext.getEvent())
                .map(EventDto::getName)
                .orElse(null);
    }

    public LocalDate getEventStartDate() {
        return Optional.ofNullable(sessionContext.getEvent())
                .map(EventDto::getStartDate)
                .orElse(null);
    }

    public LocalDate getEventEndDate() {
        return Optional.ofNullable(sessionContext.getEvent())
                .map(EventDto::getEndDate)
                .orElse(null);
    }

    private List<ParticipantDto> getParticipants(UserDto authenticatedUser) {
        return Optional.ofNullable(authenticatedUser)
                .map(participantService::findByUser)
                .orElse(Collections.emptyList());
    }

    private List<EventDto> getAllEvents(List<ParticipantDto> participants) {
        return participants.stream()
                .map(ParticipantDto::getEvent)
                .collect(Collectors.toList());
    }

    private List<EventDto> getOngoingEvents(List<ParticipantDto> participants) {
        LocalDate now = LocalDate.now();
        return participants.stream()
                .map(ParticipantDto::getEvent)
                .filter(e -> e.getStartDate().isEqual(now) || e.getStartDate().isBefore(now))
                .filter(e -> e.getEndDate().isEqual(now) || e.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    private EventDto getLast(List<EventDto> events) {
        return events.stream().max(Comparator.comparing(EventDto::getName)).orElse(null);
    }

    private static ParticipantDto getParticipant(List<ParticipantDto> participants, EventDto event) {
        return event == null ? null : participants.stream()
                .filter(p -> p.getEvent().equals(event))
                .findFirst().orElse(null);
    }
}
