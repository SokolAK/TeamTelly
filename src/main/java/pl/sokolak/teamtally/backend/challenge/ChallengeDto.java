package pl.sokolak.teamtally.backend.challenge;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
public class ChallengeDto extends Data {
    private String name;
    private Integer personalPoints;
    private Integer teamPoints;
    private EventDto event;
    private Set<TagDto> tags;
}
