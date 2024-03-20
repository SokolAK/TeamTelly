package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.Event;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class ParticipantService implements ServiceWithEvent<ParticipantDto> {

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

    public Optional<ParticipantDto> findById(UUID uuid) {
        return participantRepository.findById(uuid)
                .map(participantMapper::toDto);
    }

    public void deleteByUserId(UserDto userDto) {
        User user = new UserMapper().toEntity(userDto);
        participantRepository.deleteAllByUser(user);
    }

    public List<ParticipantDto> findByUser(UserDto userDto) {
        return participantRepository.findAllByUser(new UserMapper().toEntity(userDto)).stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipantDto> findAllByEvent(EventDto event) {
        return participantRepository.findAllByEvent(new EventMapper().toEntity(event)).stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }
}
