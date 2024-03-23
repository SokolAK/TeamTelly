package pl.sokolak.teamtally.backend.tag;

import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
public class TagMapper {
    public TagDto toDto(Tag entity) {
        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public Tag toEntity(TagDto dto) {
        return Tag.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .build();
    }

    public Set<TagDto> toDtos(Collection<Tag> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public Set<Tag> toEntities(Collection<TagDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
