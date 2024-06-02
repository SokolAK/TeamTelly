package pl.sokolak.teamtally.backend.history;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HistoryService {

    private final HistoryRepository repository;
    private final Mapper mapper;

    public void save(UserDto user, String operation) {
        repository.save(History.builder()
                .username(user.getUsername())
                .operation(operation)
                .timestamp(Instant.now())
                .build());
    }

    public List<HistoryDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
