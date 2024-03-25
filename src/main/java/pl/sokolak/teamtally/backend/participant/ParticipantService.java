package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.Mapper;
import pl.sokolak.teamtally.backend.ServiceWithEvent;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public Optional<ParticipantDto> findById(UUID uuid) {
        return participantRepository.findById(uuid)
                .map(mapper::toDto);
    }

    public void deleteByUserId(UserDto userDto) {
        User user = new UserMapper().toEntity(userDto);
        participantRepository.deleteAllByUser(user);
    }

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

    public List<ParticipantDto> findAllActiveByEvent(EventDto event) {
        if(event == null) {
            return Collections.emptyList();
        }
        return participantRepository.findAllByEventAndActive(mapper.toEntity(event), true).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
