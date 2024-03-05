package pl.sokolak.teamtally.activity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityConfiguration {

    @Bean
    public ActivityService activityService(ActivityRepository repository) {
        return new ActivityService(repository, activityMapper());
    }

    public ActivityMapper activityMapper() {
        return new ActivityMapper();
    }
}
