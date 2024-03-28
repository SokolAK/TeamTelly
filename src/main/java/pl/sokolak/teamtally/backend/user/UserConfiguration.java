package pl.sokolak.teamtally.backend.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;
import pl.sokolak.teamtally.backend.user.role.RoleService;

@Configuration
public class UserConfiguration {

    @Bean
    public UserService userService(UserRepository repository, RoleService roleService) {
        return new UserService(repository, roleService, new MapperImpl());
    }
}
