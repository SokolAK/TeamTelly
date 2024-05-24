package pl.sokolak.teamtally.backend.suggestion;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class SuggestionService implements ServiceWithEvent<SuggestionDto> {

    private final SuggestionRepository suggestionRepository;
    private final Mapper mapper;

    @Override
    public SuggestionDto save(SuggestionDto suggestion) {
        Suggestion entity = mapper.toEntity(suggestion);
        Suggestion savedEntity = suggestionRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<SuggestionDto> findAll() {
        return suggestionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(SuggestionDto suggestion) {
        Suggestion entity = mapper.toEntity(suggestion);
        suggestionRepository.delete(entity);
    }

    @Override
    public List<SuggestionDto> findAllByEvent(EventDto event) {
        return suggestionRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Set<SuggestionDataView> findAllData() {
        return suggestionRepository.findAllData().stream()
                .map(s -> SuggestionDataView.builder()
                        .id((Integer) s.get("id"))
                        .userId((Integer) s.get("user_id"))
                        .eventId((Integer) s.get("event_id"))
                        .text(getStringField(s.get("text")))
                        .build())
                .collect(Collectors.toSet());
    }
}
