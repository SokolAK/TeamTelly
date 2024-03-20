package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ChallengeService implements ServiceWithEvent<ChallengeDto> {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

    @Override
    public ChallengeDto save(ChallengeDto challenge) {
        Challenge entity = challengeMapper.toEntity(challenge);
        Challenge savedEntity = challengeRepository.save(entity);
        return challengeMapper.toDto(savedEntity);
    }

    @Override
    public List<ChallengeDto> findAll() {
        return challengeRepository.findAll().stream()
                .map(challengeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ChallengeDto challenge) {
        Challenge entity = challengeMapper.toEntity(challenge);
        challengeRepository.delete(entity);
    }

    @Override
    public List<ChallengeDto> findAllByEvent(EventDto event) {
        return challengeRepository.findAllByEvent(new EventMapper().toEntity(event)).stream()
                .map(challengeMapper::toDto)
                .collect(Collectors.toList());
    }
}
