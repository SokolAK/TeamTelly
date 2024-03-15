package pl.sokolak.teamtally.backend.event;

import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDto implements Data {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDto owner;
    Set<ParticipantDto> participants;
}
