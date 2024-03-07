package pl.sokolak.teamtally.backend.team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
