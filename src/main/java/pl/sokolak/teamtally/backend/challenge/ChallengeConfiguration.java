package pl.sokolak.teamtally.backend.challenge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChallengeConfiguration {

    @Bean
    public ChallengeService challengeService(ChallengeRepository repository) {
        return new ChallengeService(repository, challengeMapper());
    }

    public ChallengeMapper challengeMapper() {
        return new ChallengeMapper();
    }
}
