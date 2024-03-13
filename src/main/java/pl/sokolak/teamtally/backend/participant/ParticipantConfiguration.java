package pl.sokolak.teamtally.backend.participant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParticipantConfiguration {

    @Bean
    public ParticipantService participantService(ParticipantRepository repository) {
        return new ParticipantService(repository, participantMapper());
    }

    public ParticipantMapper participantMapper() {
        return new ParticipantMapper();
    }
}
