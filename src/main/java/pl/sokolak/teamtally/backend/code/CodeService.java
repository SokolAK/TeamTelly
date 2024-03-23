package pl.sokolak.teamtally.backend.code;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class CodeService implements ServiceWithEvent<CodeDto> {

    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;

    @Override
    public CodeDto save(CodeDto challenge) {
        Code entity = codeMapper.toEntity(challenge);
        Code savedEntity = codeRepository.save(entity);
        return codeMapper.toDto(savedEntity);
    }

    @Override
    public List<CodeDto> findAll() {
        return codeRepository.findAll().stream()
                .map(codeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(CodeDto challenge) {
        Code entity = codeMapper.toEntity(challenge);
        codeRepository.delete(entity);
    }

    @Override
    public List<CodeDto> findAllByEvent(EventDto event) {
        return codeRepository.findAllByEvent(new EventMapper().toEntity(event)).stream()
                .map(codeMapper::toDto)
                .collect(Collectors.toList());
    }
}
