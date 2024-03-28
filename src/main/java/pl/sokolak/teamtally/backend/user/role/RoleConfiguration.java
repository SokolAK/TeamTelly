package pl.sokolak.teamtally.backend.user.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class RoleConfiguration {

    @Bean
    public RoleService roleService(RoleRepository repository) {
        return new RoleService(repository, new MapperImpl());
    }
}
