package pl.sokolak.teamtally.backend.challenge;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.backend.event.Event;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
    @ManyToOne
    private Event event;
}
