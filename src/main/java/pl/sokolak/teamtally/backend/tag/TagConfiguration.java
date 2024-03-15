package pl.sokolak.teamtally.backend.tag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TagConfiguration {

    @Bean
    public TagService tagService(TagRepository repository) {
        return new TagService(repository, tagMapper());
    }

    public TagMapper tagMapper() {
        return new TagMapper();
    }
}
