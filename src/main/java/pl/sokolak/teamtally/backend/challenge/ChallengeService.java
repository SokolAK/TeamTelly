package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.participant.ChallengeRankingDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ChallengeService implements ServiceWithEvent<ChallengeDto> {

    private final ChallengeRepository challengeRepository;
    private final ChallengeRankingRepository challengeRankingRepository;
    private final Mapper mapper;

    @Override
    public ChallengeDto save(ChallengeDto challenge) {
        Challenge entity = mapper.toEntity(challenge);
        Challenge savedEntity = challengeRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<ChallengeDto> findAll() {
        return challengeRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ChallengeDto challenge) {
        Challenge entity = mapper.toEntity(challenge);
        challengeRepository.delete(entity);
    }

    @Override
    public List<ChallengeDto> findAllByEvent(EventDto event) {
        return challengeRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Set<ChallengeRankingDto> findAllForRankingByIdIn(Set<Integer> ids) {
        return challengeRankingRepository.findAllByIdIn(ids).stream()
                .map(c -> new ChallengeRankingDto(
                                (Integer) c.get("id"),
                                String.valueOf(c.get("name")),
                                (Integer) c.get("individual_points"),
                                (Integer) c.get("team_points")
                        )
                )
                .collect(Collectors.toSet());
    }
}
