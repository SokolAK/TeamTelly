package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.util.SerializationUtils;
import pl.sokolak.teamtally.abstracts.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.mapper.Mapper;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.*;
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

    @Override
    public void delete(ParticipantDto participant) {
        Participant entity = mapper.toEntity(participant);
        participantRepository.delete(entity);
    }

    public Optional<ParticipantDto> findById(Integer id) {
        return participantRepository.findById(id)
                .map(mapper::toDto);
    }

//    public void deleteByUserId(UserDto userDto) {
//        User user = new UserMapper().toEntity(userDto);
//        participantRepository.deleteAllByUser(user);
//    }

    public List<ParticipantDto> findByUser(UserDto userDto) {
        return participantRepository.findAllByUser(mapper.toEntity(userDto)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipantDto> findAllByEvent(EventDto event) {
        return participantRepository.findAllByEvent(mapper.toEntity(event)).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

//    public List<ParticipantDto> findAllActiveByEvent(EventDto event) {
//        if (event == null) {
//            return Collections.emptyList();
//        }
//        return participantRepository.getAllByEventAndActive(mapper.toEntity(event), true, ParticipantRankingView.class).stream()
//                .map(mapper::toDto)
//                .collect(Collectors.toList());
//    }


    public Set<ParticipantRankingDto> findAllActiveByEvent(EventDto event) {
        if (event == null) {
            return Collections.emptySet();
        }
        return participantRepository.getAllByEvent(mapper.toEntity(event).getId()).stream()
                .map(p -> new ParticipantRankingDto(
                        (Integer) p.get("id"),
                        Optional.ofNullable(p.get("username")).map(String.class::cast).orElse(null),
                        Optional.ofNullable(p.get("first_name")).map(String.class::cast).orElse(null),
                        Optional.ofNullable(p.get("last_name")).map(String.class::cast).orElse(null),
                        Optional.ofNullable(p.get("job_title")).map(String.class::cast).orElse(null),
                        (byte[]) p.get("photo"),
                        Optional.ofNullable(p.get("icon")).map(String.class::cast).orElse(null),
                        Optional.ofNullable(p.get("name")).map(String.class::cast).orElse(null),
                        Optional.ofNullable(p.get("color")).map(String.class::cast).orElse(null)
                )).collect(Collectors.toSet());
    }

    public Set<ParticipantChallenge> findCompletedChallengesForEvent(EventDto event) {
        if (event == null) {
            return Collections.emptySet();
        }

        List<Map<String, Integer>> allCompletedChallengesForEvent = participantRepository.getAllCompletedChallengesForEvent(mapper.toEntity(event).getId());
        return allCompletedChallengesForEvent.stream()
                .map(c -> new ParticipantChallenge(c.get("participantId"), c.get("challengeId"))).collect(Collectors.toSet());
    }
}
