package pl.sokolak.teamtally.backend.tag;

import jakarta.persistence.ManyToOne;
import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDto implements Data {
    private UUID id;
    private String name;
}
