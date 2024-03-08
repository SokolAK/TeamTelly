package pl.sokolak.teamtally.backend.challenge;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChallengeDto {
    private UUID id;
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
}
