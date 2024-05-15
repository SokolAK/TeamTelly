package pl.sokolak.teamtally.backend.code;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CodeDto extends Data {
    private String code;
    private boolean active;
    private int usages;
    private int maxUsages;
    private EventDto event;
    private ChallengeDto challenge;

    public boolean canBeUsed() {
        return active && usages < maxUsages;
    }

    public void use() {
        usages++;
        if(usages >= maxUsages) {
            active = false;
        }
    }
}
