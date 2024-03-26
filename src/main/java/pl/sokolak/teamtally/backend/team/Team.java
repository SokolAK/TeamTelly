package pl.sokolak.teamtally.backend.team;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.participant.Participant;

import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Team extends AbstractEntityWithEvent {
    private String name;
    private String color;
    private String icon;
    @OneToMany(mappedBy = "team")
    private Set<Participant> participants;
}
