package pl.sokolak.teamtally.team;

import pl.sokolak.teamtally.activity.Activity;
import pl.sokolak.teamtally.activity.ActivityDto;

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
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .color(dto.getColor())
                .icon(dto.getIcon())
                .build();
    }
}
