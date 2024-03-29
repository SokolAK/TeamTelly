package pl.sokolak.teamtally.abstracts;

import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.List;

public interface ServiceWithEvent<T extends Data> extends Service<T> {
    List<T> findAllByEvent(EventDto event);
}
