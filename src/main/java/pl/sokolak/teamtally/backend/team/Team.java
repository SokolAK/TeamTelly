package pl.sokolak.teamtally.backend.team;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.EntityWithEvent;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Team extends EntityWithEvent {
    private String name;
    private String color;
    private String icon;
}
