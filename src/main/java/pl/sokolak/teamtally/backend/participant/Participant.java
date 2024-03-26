package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.team.Team;
import pl.sokolak.teamtally.backend.user.User;

import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Participant extends AbstractEntityWithEvent {
    private Boolean active;
    @ManyToOne
    private Team team;
    @ManyToOne
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "participant_challenge",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id"))
    private Set<Challenge> completedChallenges;
}
