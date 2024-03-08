package pl.sokolak.teamtally.backend.challenge;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

    public List<ChallengeDto> findAll() {
        return challengeRepository.findAll().stream()
                .map(challengeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChallengeDto save(ChallengeDto challenge) {
        Challenge entity = challengeMapper.toEntity(challenge);
        Challenge savedEntity = challengeRepository.save(entity);
        return challengeMapper.toDto(savedEntity);
    }

    public void delete(ChallengeDto challenge) {
        Challenge entity = challengeMapper.toEntity(challenge);
        challengeRepository.delete(entity);
    }
}
