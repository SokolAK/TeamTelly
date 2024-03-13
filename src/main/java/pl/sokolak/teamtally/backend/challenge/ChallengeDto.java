package pl.sokolak.teamtally.backend.challenge;

import jakarta.persistence.ManyToOne;
import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChallengeDto implements Data {
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
    @ManyToOne
    private EventDto event;
}
