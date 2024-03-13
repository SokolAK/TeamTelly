package pl.sokolak.teamtally.backend.session;

import lombok.Getter;
import lombok.Setter;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;

@Getter
@Setter
public class SessionContext {
    private ParticipantDto participant;
    private EventDto event;
}
