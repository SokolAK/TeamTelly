package pl.sokolak.teamtally.abstracts;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.event.Event;

@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntityWithEvent extends AbstractEntity {
    @ManyToOne
    private Event event;
}
