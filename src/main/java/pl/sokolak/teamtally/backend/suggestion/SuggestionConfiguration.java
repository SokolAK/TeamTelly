package pl.sokolak.teamtally.backend.suggestion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class SuggestionConfiguration {

    @Bean
    public SuggestionService suggestionService(SuggestionRepository repository) {
        return new SuggestionService(repository, new MapperImpl());
    }
}
