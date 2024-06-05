package pl.sokolak.teamtally.backend.challenge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class ChallengeConfiguration {

    @Bean
    public ChallengeService challengeService(ChallengeRepository repository, CodeService codeService) {
        return new ChallengeService(repository, codeService, new MapperImpl());
    }
}
