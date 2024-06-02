package pl.sokolak.teamtally.backend.history;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.event.EventRepository;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class HistoryConfiguration {

    @Bean
    public HistoryService historyService(HistoryRepository repository) {
        return new HistoryService(repository, new MapperImpl());
    }
}
