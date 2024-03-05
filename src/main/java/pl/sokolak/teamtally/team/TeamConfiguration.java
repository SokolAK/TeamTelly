package pl.sokolak.teamtally.team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.activity.ActivityMapper;
import pl.sokolak.teamtally.activity.ActivityRepository;
import pl.sokolak.teamtally.activity.ActivityService;

@Configuration
public class TeamConfiguration {

    @Bean
    public TeamService teamService(TeamRepository repository) {
        return new TeamService(repository, teamMapper());
    }

    public TeamMapper teamMapper() {
        return new TeamMapper();
    }
}
