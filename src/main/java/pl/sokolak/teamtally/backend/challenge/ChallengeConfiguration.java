package pl.sokolak.teamtally.backend.challenge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class ChallengeConfiguration {

    @Bean
    public ChallengeService challengeService(ChallengeRepository repository) {
        return new ChallengeService(repository, new MapperImpl());
    }
}
