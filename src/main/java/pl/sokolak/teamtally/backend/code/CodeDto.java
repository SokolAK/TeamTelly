package pl.sokolak.teamtally.backend.code;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;

@SuperBuilder
@Getter
@Setter
public class CodeDto extends Data {
    private String code;
    private boolean active;
    private EventDto event;
    private ChallengeDto challenge;
}
