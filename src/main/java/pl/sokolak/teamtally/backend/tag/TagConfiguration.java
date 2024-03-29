package pl.sokolak.teamtally.backend.tag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class TagConfiguration {

    @Bean
    public TagService tagService(TagRepository repository) {
        return new TagService(repository, new MapperImpl());
    }
}
