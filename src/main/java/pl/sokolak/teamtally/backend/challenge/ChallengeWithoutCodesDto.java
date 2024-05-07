package pl.sokolak.teamtally.backend.challenge;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
public class ChallengeWithoutCodesDto extends Data {
    private String name;
    private String description;
    private Integer individualPoints;
    private Integer teamPoints;
    private EventDto event;
    private Set<TagDto> tags;
}
