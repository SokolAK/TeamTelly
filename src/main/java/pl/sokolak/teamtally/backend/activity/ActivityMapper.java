package pl.sokolak.teamtally.backend.activity;

import java.util.UUID;

public class ActivityMapper {
    public ActivityDto toDto(Activity entity) {
        return ActivityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .personalPoints(entity.getPersonalPoints())
                .teamPoints(entity.getTeamPoints())
                .build();
    }

    public Activity toEntity(ActivityDto dto) {
        return Activity.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .personalPoints(dto.getPersonalPoints())
                .teamPoints(dto.getTeamPoints())
                .build();
    }
}
