package pl.sokolak.teamtally.backend.suggestion;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import pl.sokolak.teamtally.abstracts.AbstractEntity;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.user.User;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Suggestion extends AbstractEntity {
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private User user;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private Event event;
    private String text;
}
