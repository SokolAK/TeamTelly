package pl.sokolak.teamtally.backend.participant;

import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantDto implements Data {
    private UUID id;
    private EventDto event;
    private UserDto user;
}
