package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ChallengeWithoutCodesService implements ServiceWithEvent<ChallengeWithoutCodesDto> {

    private final ChallengeRepository challengeRepository;
    private final Mapper mapper;

    @Override
    public ChallengeWithoutCodesDto save(ChallengeWithoutCodesDto challenge) {
        Challenge entity = mapper.toEntityWithoutCodes(challenge);
        Challenge savedEntity = challengeRepository.save(entity);
        return mapper.toDtoWithoutCodes(savedEntity);
    }

    @Override
    public List<ChallengeWithoutCodesDto> findAll() {
        return challengeRepository.findAll().stream()
                .map(mapper::toDtoWithoutCodes)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ChallengeWithoutCodesDto challenge) {
        Challenge entity = mapper.toEntityWithoutCodes(challenge);
        challengeRepository.delete(entity);
    }

    @Override
    public List<ChallengeWithoutCodesDto> findAllByEvent(EventDto event) {
        return challengeRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(mapper::toDtoWithoutCodes)
                .collect(Collectors.toList());
    }
}
