package pl.sokolak.teamtally.backend.event;

import java.util.UUID;

public class EventMapper {
    public EventDto toDto(Event entity) {
        return EventDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .date(entity.getDate())
                .owner(entity.getOwner())
                .build();
    }

    public Event toEntity(EventDto dto) {
        return Event.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .date(dto.getDate())
                .owner(dto.getOwner())
                .build();
    }
}
