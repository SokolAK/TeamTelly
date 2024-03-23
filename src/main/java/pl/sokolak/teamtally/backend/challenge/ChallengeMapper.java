package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.tag.TagMapper;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
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

    public Set<ChallengeDto> toDtos(Set<Challenge> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public Set<Challenge> toEntities(Set<ChallengeDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
