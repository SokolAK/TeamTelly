package pl.sokolak.teamtally.backend.challenge;

import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChallengeService implements Service<ChallengeDto> {

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
}
