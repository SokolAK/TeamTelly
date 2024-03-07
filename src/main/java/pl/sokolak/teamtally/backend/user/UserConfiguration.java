package pl.sokolak.teamtally.backend.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.activity.ActivityMapper;
import pl.sokolak.teamtally.backend.activity.ActivityRepository;
import pl.sokolak.teamtally.backend.activity.ActivityService;

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
