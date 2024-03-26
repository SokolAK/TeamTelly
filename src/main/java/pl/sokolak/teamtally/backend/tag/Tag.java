package pl.sokolak.teamtally.backend.tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;
import pl.sokolak.teamtally.backend.challenge.Challenge;

import java.util.Set;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends AbstractEntity {
    private String name;
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private Set<Challenge> challenges;
}
