package pl.sokolak.teamtally.backend.challenge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.tag.Tag;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
    @ManyToOne
    private Event event;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "challenge_tag",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
