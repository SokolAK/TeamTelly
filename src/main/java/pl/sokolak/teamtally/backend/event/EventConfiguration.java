package pl.sokolak.teamtally.backend.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class EventConfiguration {

    @Bean
    public EventService eventService(EventRepository repository) {
        return new EventService(repository, new MapperImpl());
    }
}
