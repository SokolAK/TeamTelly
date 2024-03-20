package pl.sokolak.teamtally.backend.participant;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
public class ParticipantDto extends Data {
    private boolean active;
    private TeamDto team;
    private EventDto event;
    private UserDto user;
}
