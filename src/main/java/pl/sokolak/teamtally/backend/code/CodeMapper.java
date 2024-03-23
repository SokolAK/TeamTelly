package pl.sokolak.teamtally.backend.code;

import jakarta.transaction.Transactional;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeMapper;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
public class CodeMapper {
    public CodeDto toDto(Code entity) {
        return CodeDto.builder()
                .id(entity.getId())
                .active(entity.isActive())
                .code(entity.getCode())
                .event(new EventMapper().toDto(entity.getEvent()))
                .challenge(new ChallengeMapper().toDto(entity.getChallenge()))
                .build();
    }

    public Code toEntity(CodeDto dto) {
        return Code.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .active(dto.isActive())
                .code(dto.getCode())
                .event(new EventMapper().toEntity(dto.getEvent()))
                .challenge(new ChallengeMapper().toEntity(dto.getChallenge()))
                .build();
    }

    public List<CodeDto> toDtos(Collection<Code> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Code> toEntities(Collection<CodeDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
