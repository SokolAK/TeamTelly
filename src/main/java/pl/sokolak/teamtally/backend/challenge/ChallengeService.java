package pl.sokolak.teamtally.backend.challenge;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.participant.ChallengeDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ChallengeService implements ServiceWithEvent<ChallengeDto> {

    private final ChallengeRepository challengeRepository;
    private final CodeService codeService;
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

    public Set<ChallengeDto> findAllDataByEventFast(EventDto event) {
        Set<ChallengeDto> challenges = findAllDataByEventId(event);
        List<CodeDto> codes = codeService.findAllByEvent(event);
        for (ChallengeDto challenge : challenges) {
            Set<CodeDto> codesForChallenge = codes.stream()
                    .filter(c -> c.getChallenge().getId().equals(challenge.getId()))
                    .collect(Collectors.toSet());
            challenge.getCodes().addAll(codesForChallenge);
        }
        return challenges;
    }


    public Set<ChallengeDto> findAllDataByEvent(EventDto event) {
        return challengeRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(c -> ChallengeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .description(c.getDescription())
                        .individualPoints(c.getIndividualPoints())
                        .teamPoints(c.getTeamPoints())
                        .event(EventDto.builder()
                                .id(c.getEvent().getId())
                                .build())
                        .codes(c.getCodes().stream().map(
                                cc -> CodeDto.builder()
                                        .id(cc.getId())
                                        .code(cc.getCode())
                                        .active(cc.isActive())
                                        .usages(cc.getUsages())
                                        .maxUsages(cc.getMaxUsages())
                                        .codeFrom(cc.getCodeFrom())
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

    public Set<ChallengeDto> findAllDataByEventId(EventDto event) {
        return challengeRepository.findAllByEventId(event.getId()).stream()
                .map(c -> ChallengeDto.builder()
                        .id((Integer) c.get("challenge_id"))
                        .name(getStringField(c.get("challenge_name")))
                        .description(getStringField(c.get("description")))
                        .individualPoints((Integer) c.get("individual_points"))
                        .teamPoints((Integer) c.get("team_points"))
                        .event(EventDto.builder()
                                .id(event.getId())
                                .build())
                        .codes(new HashSet<>())
                        .tags(new HashSet<>())
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

    public Set<Integer> findAllIdsCompletedByParticipant(ParticipantDto participant) {
        if(participant == null) {
            return Set.of();
        }
        return challengeRepository.findAllIdsCompletedByParticipant(participant.getId()).stream()
                .map(c -> (Integer) c.get("id"))
                .collect(Collectors.toSet());
    }

    public Set<Integer> findAllIdsCompletedByTeam(TeamDto team) {
//        int teamSize = Optional.ofNullable(team)
//                .map(TeamDto::getParticipants)
//                .orElse(Set.of())
//                .size();
        if(team == null) {
            return Set.of();
        }
        return challengeRepository.findAllIdsCompletedByTeam(team.getId()).stream()
                .map(c -> (Integer) c.get("id"))
                .collect(Collectors.toSet());
    }

    public void updateOrSave(ChallengeDto challenge) {
        if (challenge.getId() == null) {
            save(challenge);
            return;
        }
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
