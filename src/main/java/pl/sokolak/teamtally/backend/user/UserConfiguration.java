package pl.sokolak.teamtally.backend.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.Mapper;
import pl.sokolak.teamtally.backend.MapperImpl;

@Configuration
public class UserConfiguration {

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository, new MapperImpl());
    }

    public UserMapper mapper() {
        return new UserMapper();
    }
}
