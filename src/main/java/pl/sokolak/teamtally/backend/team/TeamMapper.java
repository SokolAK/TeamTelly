package pl.sokolak.teamtally.backend.team;

import jakarta.transaction.Transactional;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.UUID;

@Transactional
public class TeamMapper {
    public TeamDto toDto(Team entity) {
        return TeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .icon(entity.getIcon())
                .event(new EventMapper().toDto(entity.getEvent()))
                .build();
    }

    public Team toEntity(TeamDto dto) {
        return Team.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .color(dto.getColor())
                .icon(dto.getIcon())
                .event(new EventMapper().toEntity(dto.getEvent()))
                .build();
    }
}
