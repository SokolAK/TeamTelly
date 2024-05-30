package pl.sokolak.teamtally.backend.code;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CodeDto extends Data {
    @NotNull
    @NotEmpty
    private String code;
    private boolean active;
    private int usages;
    private Integer maxUsages;
    private String codeFrom;
    private EventDto event;
    @NotNull
    private ChallengeDto challenge;

    public boolean canBeUsed() {
        return active && (maxUsages == null || usages < maxUsages);
    }

    public void use() {
        if(maxUsages == null) {
            return;
        }
        usages++;
        if(usages >= maxUsages) {
            active = false;
        }
    }
}
