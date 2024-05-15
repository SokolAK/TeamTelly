package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ParticipantService implements ServiceWithEvent<ParticipantDto> {

    private final ParticipantRepository participantRepository;
    private final Mapper mapper;

    @Override
    public ParticipantDto save(ParticipantDto participant) {
        Participant entity = mapper.toEntity(participant);
        Participant savedEntity = participantRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public List<ParticipantDto> findAll() {
        return participantRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ParticipantDto findById(Integer id) {
        return participantRepository.findById(id)
                .map(p -> mapper.toDto(p))
                .orElse(null);
    }

    @Override
    public void delete(ParticipantDto participant) {
        Participant entity = mapper.toEntity(participant);
        participantRepository.delete(entity);
    }

    public List<ParticipantDto> findByUser(UserDto userDto) {
        return participantRepository.findAllByUser(mapper.toEntity(userDto)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ParticipantDto> findAllByEvent(EventDto event) {
//        return participantRepository.findAllByEvent(mapper.toEntity(event)).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ParticipantDto> findAllByEvent(EventDto event) {
        return participantRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(p -> ParticipantDto.builder()
                        .id(p.getId())
                        .team(p.getTeam() != null ? TeamDto.builder()
                                .id(p.getTeam().getId())
                                .name(p.getTeam().getName())
                                .icon(p.getTeam().getIcon())
                                .color(p.getTeam().getColor())
                                .build() : null)
                        .user(UserDto.builder()
                                .id(p.getUser().getId())
                                .username(p.getUser().getUsername())
                                .firstName(p.getUser().getFirstName())
                                .lastName(p.getUser().getLastName())
                                .jobTitle(p.getUser().getJobTitle())
                                .photo(p.getUser().getPhoto())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public Set<ParticipantDataView> findAllActiveDataByEvent(EventDto event) {
        if (event == null) {
            return Collections.emptySet();
        }
        return participantRepository.getAllByEvent(mapper.toEntity(event).getId()).stream()
                .map(p -> new ParticipantDataView(
                        (Integer) p.get("id"),
                        getStringField(p.get("username")),
                        getStringField(p.get("first_name")),
                        getStringField(p.get("last_name")),
                        getStringField(p.get("job_title")),
                        (byte[]) p.get("photo"),
                        (Integer) p.get("team_id")
                )).collect(Collectors.toSet());
    }

    public Set<ParticipantChallengeRankingView> findParticipantChallengesForRankingByEvent(EventDto event) {
        if (event == null) {
            return Collections.emptySet();
        }
        return participantRepository.getParticipantChallenges(mapper.toEntity(event).getId()).stream()
                .map(c -> new ParticipantChallengeRankingView(
                        c.get("participantId"),
                        c.get("challengeId"))
                ).collect(Collectors.toSet());
    }

    public void updateTeam(ParticipantDto participant, TeamDto team) {
        participantRepository.updateTeam(participant.getId(), team.getId());
    }

    private String getStringField(Object value) {
        return Optional.ofNullable(value).map(String.class::cast).orElse(null);
    }



}
