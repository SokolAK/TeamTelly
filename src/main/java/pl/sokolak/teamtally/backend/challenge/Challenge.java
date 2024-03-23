package pl.sokolak.teamtally.backend.challenge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.EntityWithEvent;
import pl.sokolak.teamtally.backend.participant.Participant;
import pl.sokolak.teamtally.backend.tag.Tag;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Challenge extends EntityWithEvent {
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "challenge_tag",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    @ManyToMany(mappedBy = "challenges", cascade = CascadeType.ALL)
    private Set<Participant> participants;
}
