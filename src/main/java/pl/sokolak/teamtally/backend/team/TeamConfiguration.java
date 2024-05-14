package pl.sokolak.teamtally.backend.team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class TeamConfiguration {

    @Bean
    public TeamService teamService(TeamRepository repository, TeamRankingRepository teamRankingRepository) {
        return new TeamService(repository, teamRankingRepository, new MapperImpl());
    }
}
