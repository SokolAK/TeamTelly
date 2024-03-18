package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ParticipantService implements Service<ParticipantDto> {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public ParticipantDto save(ParticipantDto event) {
        Participant entity = participantMapper.toEntity(event);
        Participant savedEntity = participantRepository.save(entity);
        return participantMapper.toDto(savedEntity);
    }

    @Override
    public List<ParticipantDto> findAll() {
        return participantRepository.findAll().stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ParticipantDto event) {
        Participant entity = participantMapper.toEntity(event);
        participantRepository.delete(entity);
    }

    public List<ParticipantDto> findByUser(UserDto userDto) {
        return participantRepository.findAllByUser(new UserMapper().toEntity(userDto)).stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ParticipantDto> findAllByEvent(EventDto event) {
        return participantRepository.findAllByEvent(new EventMapper().toEntity(event)).stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }
}
