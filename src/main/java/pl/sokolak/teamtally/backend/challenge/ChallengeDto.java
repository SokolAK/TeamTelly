package pl.sokolak.teamtally.backend.challenge;

import lombok.*;
import pl.sokolak.teamtally.backend.Data;

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
}
