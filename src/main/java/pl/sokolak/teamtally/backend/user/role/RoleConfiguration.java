package pl.sokolak.teamtally.backend.user.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.user.UserMapper;
import pl.sokolak.teamtally.backend.user.UserRepository;
import pl.sokolak.teamtally.backend.user.UserService;

@Configuration
public class RoleConfiguration {

    @Bean
    public RoleService roleService(RoleRepository repository) {
        return new RoleService(repository);
    }
}
