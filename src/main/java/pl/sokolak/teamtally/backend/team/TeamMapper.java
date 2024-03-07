package pl.sokolak.teamtally.backend.team;

import java.util.UUID;

public class TeamMapper {
    public TeamDto toDto(Team entity) {
        return TeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .icon(entity.getIcon())
                .build();
    }

    public Team toEntity(TeamDto dto) {
        return Team.builder()
                .id(dto.id() != null ? dto.id() : UUID.randomUUID())
                .name(dto.name())
                .color(dto.color())
                .icon(dto.icon())
                .build();
    }
}
