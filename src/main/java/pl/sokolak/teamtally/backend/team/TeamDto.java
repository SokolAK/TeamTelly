package pl.sokolak.teamtally.backend.team;

import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamDto implements Data {
    private UUID id;
    private String name;
    private String color;
    private String icon;
    private EventDto event;
}
