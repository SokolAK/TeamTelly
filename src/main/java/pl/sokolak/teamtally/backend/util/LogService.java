package pl.sokolak.teamtally.backend.util;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
@NoArgsConstructor
public class LogService {

    private String username;

    public LogService(SessionService sessionService) {
        this.username = Optional.ofNullable(sessionService)
                .map(SessionService::getUser)
                .map(UserDto::getUsername)
                .orElse("undefined");
    }

    public void info(String template, Object... values) {
        List<Object> objects = new ArrayList<>();
        objects.add(username);
        objects.addAll(Stream.of(values).toList());
        String message = "[{}] " + template;
        log.info(message, objects.toArray());
    }
}
