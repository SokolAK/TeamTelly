package pl.sokolak.teamtally.abstracts;

import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.List;
import java.util.Optional;

public interface ServiceWithEvent<T extends Data> extends Service<T> {
    List<T> findAllByEvent(EventDto event);
}
