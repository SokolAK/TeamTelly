package pl.sokolak.teamtally.backend.participant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sokolak.teamtally.backend.mapper.MapperImpl;

@Configuration
public class ParticipantConfiguration {

    @Bean
    public ParticipantService participantService(ParticipantRepository repository) {
        return new ParticipantService(repository, new MapperImpl());
    }
}
