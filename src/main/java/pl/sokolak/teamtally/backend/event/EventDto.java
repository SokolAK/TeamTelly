package pl.sokolak.teamtally.backend.event;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
public class EventDto extends Data {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDto owner;
    Set<ParticipantDto> participants;
}
