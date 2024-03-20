package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.EntityWithEvent;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.team.Team;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.User;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Participant extends EntityWithEvent {
    private Boolean active;
    @ManyToOne
    private Team team;
    @ManyToOne
    private User user;
}
