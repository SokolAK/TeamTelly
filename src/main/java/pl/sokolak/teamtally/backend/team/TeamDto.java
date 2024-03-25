package pl.sokolak.teamtally.backend.team;

import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class TeamDto extends Data {
    private String name;
    private String color;
    private String icon;
    private EventDto event;
    private Set<ParticipantDto> participants;

    @Override
    public String toString() {
        return icon + " " + name;
    }
}
