package pl.sokolak.teamtally.backend.challenge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.code.Code;
import pl.sokolak.teamtally.backend.participant.Participant;
import pl.sokolak.teamtally.backend.tag.Tag;

import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Challenge extends AbstractEntityWithEvent {
    private String name;
    private String description;
    private Integer individualPoints;
    private Integer teamPoints;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "challenge_tag",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    @ManyToMany(mappedBy = "completedChallenges", cascade = CascadeType.MERGE)
    private Set<Participant> participants;
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Code> codes;
}
