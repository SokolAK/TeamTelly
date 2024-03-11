package pl.sokolak.teamtally.backend.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public EventService eventService(EventRepository repository) {
        return new EventService(repository, eventMapper());
    }

    public EventMapper eventMapper() {
        return new EventMapper();
    }
}
