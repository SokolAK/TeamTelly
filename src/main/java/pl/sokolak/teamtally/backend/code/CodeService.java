package pl.sokolak.teamtally.backend.code;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class CodeService implements ServiceWithEvent<CodeDto> {

    private final CodeRepository codeRepository;
    private final Mapper mapper;

    @Override
    public CodeDto save(CodeDto code) {
        Optional<Code> maybeCode = Optional.ofNullable(code.getId())
                .map(codeRepository::findById)
                .flatMap(c -> c);
        if(maybeCode.isPresent()) {
            Code entity = mapper.toEntity(code);
            Code savedEntity = codeRepository.save(entity);
            return mapper.toDto(savedEntity);
        } else {
            Code entity = mapper.toEntity(code);
            Code savedEntity = codeRepository.save(entity);
            return mapper.toDto(savedEntity);
        }
    }

    private CodeDto persist(CodeDto code) {
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
        return codeRepository.findAllByEvent(event.getId()).stream()
                .map(c -> CodeDto.builder()
                        .id((Integer) c.get("code_id"))
                        .code(getStringField(c.get("code")))
                        .active((boolean) c.get("active"))
                        .usages((Integer) c.get("usages"))
                        .maxUsages((Integer) c.get("max_usages"))
                        .codeFrom((String) c.get("code_from"))
                        .challenge(ChallengeDto.builder()
                                .id((Integer) c.get("challenge_id"))
                                .name(getStringField(c.get("name")))
                                .individualPoints((Integer) c.get("individual_points"))
                                .teamPoints((Integer) c.get("team_points"))
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}
