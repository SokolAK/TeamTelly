package pl.sokolak.teamtally.backend.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.challenge.ChallengeMapper;
import pl.sokolak.teamtally.backend.challenge.ChallengeRepository;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;

@Configuration
public class UserConfiguration {

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository, mapper());
    }

    public UserMapper mapper() {
        return new UserMapper();
    }
}
