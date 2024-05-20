package pl.sokolak.teamtally.backend.code;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Code extends AbstractEntityWithEvent {
    private String code;
    private boolean active;
    private int usages;
    private Integer maxUsages;
    @ManyToOne()
    private Challenge challenge;
}
