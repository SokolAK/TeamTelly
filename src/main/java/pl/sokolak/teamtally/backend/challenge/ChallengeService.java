package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.participant.ChallengeDataView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ChallengeService implements ServiceWithEvent<ChallengeDto> {

    private final ChallengeRepository challengeRepository;
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

    public Set<ChallengeDto> findAllDataByEvent(EventDto event) {
        return challengeRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(c -> ChallengeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .description(c.getDescription())
                        .individualPoints(c.getIndividualPoints())
                        .teamPoints(c.getTeamPoints())
                        .event(event)
                        .codes(c.getCodes().stream().map(
                                cc -> CodeDto.builder()
                                        .id(cc.getId())
                                        .code(cc.getCode())
                                        .active(cc.isActive())
                                        .usages(cc.getUsages())
                                        .maxUsages(cc.getMaxUsages())
//                                        .event(EventDto.builder()
//                                                .id(cc.getEvent().getId())
//                                                .build())
//                                        .challenge(ChallengeDto.builder()
//                                                .id(cc.getChallenge().getId())
//                                                .build())
                                        .build()
                        ).collect(Collectors.toSet()))
                        .tags(c.getTags().stream().map(mapper::toDto).collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toSet());
    }

    public Set<ChallengeDataView> findAllForRankingByIdIn(Set<Integer> ids) {
        return challengeRepository.findAllByIdIn(ids).stream()
                .map(c -> new ChallengeDataView(
                                (Integer) c.get("id"),
                                String.valueOf(c.get("name")),
                                (Integer) c.get("individual_points"),
                                (Integer) c.get("team_points")
                        )
                )
                .collect(Collectors.toSet());
    }

    public void updateOrSave(ChallengeDto challenge) {
        challengeRepository.findById(challenge.getId())
                .ifPresentOrElse(
                        entity -> update(entity, challenge),
                        () -> save(challenge)
                );
    }

    private void update(Challenge entity, ChallengeDto challenge) {
        entity.setName(challenge.getName());
        entity.setDescription(challenge.getDescription());
        entity.setIndividualPoints(challenge.getIndividualPoints());
        entity.setTeamPoints(challenge.getTeamPoints());
        entity.setTags(challenge.getTags().stream()
                .map(mapper::toEntity)
                .collect(Collectors.toSet()));
        challengeRepository.save(entity);
    }
}
