package pl.sokolak.teamtally.backend.history;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.AbstractEntity;

import java.time.Instant;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class History extends AbstractEntity {
    private Instant timestamp;
    private String username;
    private String operation;
}
