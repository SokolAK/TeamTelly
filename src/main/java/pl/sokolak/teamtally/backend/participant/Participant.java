package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.code.Code;
import pl.sokolak.teamtally.backend.team.Team;
import pl.sokolak.teamtally.backend.user.User;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Participant extends AbstractEntityWithEvent {
    private Boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participant_code",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "code_id"))
    private List<Code> usedCodes;
}
