package pl.sokolak.teamtally.backend.event;

import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EventService implements Service<EventDto> {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventDto save(EventDto event) {
        Event entity = eventMapper.toEntity(event);
        Event savedEntity = eventRepository.save(entity);
        return eventMapper.toDto(savedEntity);
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(EventDto event) {
        Event entity = eventMapper.toEntity(event);
        eventRepository.delete(entity);
    }

    public List<EventDto> findAllOngoing(LocalDate date) {
        return eventRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
}
