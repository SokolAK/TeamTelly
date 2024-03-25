package pl.sokolak.teamtally.backend.team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.MapperImpl;

@Configuration
public class TeamConfiguration {

    @Bean
    public TeamService teamService(TeamRepository repository) {
        return new TeamService(repository, teamMapper(), new MapperImpl());
    }

    public TeamMapper teamMapper() {
        return new TeamMapper();
    }
}
