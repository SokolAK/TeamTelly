package pl.sokolak.teamtally.backend.tag;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    private UUID id;
    private String name;
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Challenge> challenges;
}
