package pl.sokolak.teamtally.backend.activity;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public List<ActivityDto> findAll() {
        return activityRepository.findAll().stream()
                .map(activityMapper::toDto)
                .collect(Collectors.toList());
    }

    public ActivityDto save(ActivityDto activity) {
        Activity entity = activityMapper.toEntity(activity);
        Activity savedEntity = activityRepository.save(entity);
        return activityMapper.toDto(savedEntity);
    }

    public void delete(ActivityDto activity) {
        Activity entity = activityMapper.toEntity(activity);
        activityRepository.delete(entity);
    }
}
