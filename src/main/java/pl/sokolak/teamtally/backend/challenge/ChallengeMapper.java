package pl.sokolak.teamtally.backend.challenge;

import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.tag.TagMapper;

import java.util.UUID;

public class ChallengeMapper {
    public ChallengeDto toDto(Challenge entity) {
        return ChallengeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .personalPoints(entity.getPersonalPoints())
                .teamPoints(entity.getTeamPoints())
                .event(new EventMapper().toDto(entity.getEvent()))
                .tags(new TagMapper().toDtos(entity.getTags()))
                .build();
    }

    public Challenge toEntity(ChallengeDto dto) {
        return Challenge.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .personalPoints(dto.getPersonalPoints())
                .teamPoints(dto.getTeamPoints())
                .event(new EventMapper().toEntity(dto.getEvent()))
                .tags(new TagMapper().toEntities(dto.getTags()))
                .build();
    }
}
