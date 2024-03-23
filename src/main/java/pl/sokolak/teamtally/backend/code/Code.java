package pl.sokolak.teamtally.backend.code;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.EntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Code extends EntityWithEvent {
    private String code;
    private boolean active;
    @OneToOne
    private Challenge challenge;
}
