package pl.sokolak.teamtally.backend.team;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;

@SuperBuilder
@Getter
@Setter
public class TeamDto extends Data {
    private String name;
    private String color;
    private String icon;
    private EventDto event;

    @Override
    public String toString() {
        return icon + " " + name;
    }
}
