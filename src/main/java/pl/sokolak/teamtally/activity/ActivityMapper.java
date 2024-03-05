package pl.sokolak.teamtally.activity;

public class ActivityMapper {

    public ActivityDto toDto(Activity entity) {
        return ActivityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .personalPoints(entity.getPersonalPoints())
                .teamPoints(entity.getTeamPoints())
                .build();
    }
}
