package pl.sokolak.teamtally.backend.code;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class CodeService implements ServiceWithEvent<CodeDto> {

    private final CodeRepository codeRepository;
    private final Mapper mapper;

    @Override
    public CodeDto save(CodeDto code) {
        Code entity = mapper.toEntity(code);
        Code savedEntity = codeRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<CodeDto> findAll() {
        return codeRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(CodeDto code) {
        Code entity = mapper.toEntity(code);
        codeRepository.delete(entity);
    }

    @Override
    public List<CodeDto> findAllByEvent(EventDto event) {
        return codeRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
