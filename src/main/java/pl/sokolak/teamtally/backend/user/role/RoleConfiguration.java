package pl.sokolak.teamtally.backend.user.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfiguration {

    @Bean
    public RoleService roleService(RoleRepository repository) {
        return new RoleService(repository);
    }
}
