package pl.sokolak.teamtally.backend.event;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.Service;
import pl.sokolak.teamtally.backend.mapper.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class EventService implements Service<EventDto> {

    private final EventRepository eventRepository;
    private final Mapper mapper;

    @Override
    public EventDto save(EventDto event) {
        Event entity = mapper.toEntity(event);
        Event savedEntity = eventRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> findAllData() {
        return eventRepository.findAll().stream()
                .map(e -> EventDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .logo(e.getLogo())
                        .build())
                .collect(Collectors.toList());
    }

    public List<EventDto> findAllDataByIds(Set<Integer> ids) {
        return eventRepository.findAllById(ids).stream()
                .map(e -> EventDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .logo(e.getLogo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(EventDto event) {
        Event entity = mapper.toEntity(event);
        eventRepository.delete(entity);
    }

    public boolean isEventOpened(EventDto event) {
        return eventRepository.isEventOpened(event.getId());
    }
}
