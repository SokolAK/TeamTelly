package pl.sokolak.teamtally.backend.challenge;

import lombok.*;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.List;
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
    private EventDto event;
    private List<TagDto> tags;
}
