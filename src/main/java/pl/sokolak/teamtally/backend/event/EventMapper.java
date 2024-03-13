package pl.sokolak.teamtally.backend.event;

import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.UUID;

public class EventMapper {
    public EventDto toDto(Event entity) {
        return EventDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .owner(new UserMapper().toDto(entity.getOwner()))
                .build();
    }

    public Event toEntity(EventDto dto) {
        return Event.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .owner(new UserMapper().toEntity(dto.getOwner()))
                .build();
    }
}
