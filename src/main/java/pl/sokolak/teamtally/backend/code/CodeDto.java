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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CodeDto codeDto = (CodeDto) o;

        if (!code.equals(codeDto.code)) return false;
        if (!event.equals(codeDto.event)) return false;
        return challenge.equals(codeDto.challenge);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + event.hashCode();
        result = 31 * result + challenge.hashCode();
        return result;
    }
}
