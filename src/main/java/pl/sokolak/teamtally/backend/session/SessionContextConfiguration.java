package pl.sokolak.teamtally.backend.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.backend.util.LogService;

@Configuration
public class SessionContextConfiguration {

    @Bean
    @Scope(WebApplicationContext.SCOPE_SESSION)
    public LogService logService(SessionService sessionService) {
        return new LogService(sessionService);
    }

    @Bean
    @Scope(WebApplicationContext.SCOPE_SESSION)
    public SessionService sessionService(SecurityService securityService, ParticipantService participantService, EventService eventService, UserService userService) {
        return new SessionService(new SessionContext(), securityService, participantService, eventService, userService);
    }
}
