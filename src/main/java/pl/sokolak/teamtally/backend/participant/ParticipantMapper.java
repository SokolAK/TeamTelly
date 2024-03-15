package pl.sokolak.teamtally.backend.participant;

import jakarta.transaction.Transactional;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
public class ParticipantMapper {
    public ParticipantDto toDto(Participant entity) {
        return ParticipantDto.builder()
                .id(entity.getId())
                .user(new UserMapper().toDto(entity.getUser()))
                .event(new EventMapper().toDto(entity.getEvent()))
                .build();
    }

    public Participant toEntity(ParticipantDto dto) {
        return Participant.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .user(new UserMapper().toEntity(dto.getUser()))
                .event(new EventMapper().toEntity(dto.getEvent()))
                .build();
    }

    public Set<ParticipantDto> toDtosWithoutUser(Collection<Participant> entities) {
        if(entities == null) return null;
        return entities.stream()
                .map(this::toDtoWithoutUser)
                .collect(Collectors.toSet());
    }

    public Set<Participant> toEntitiesWithoutUser(Collection<ParticipantDto> dtos) {
        if(dtos == null) return null;
        return dtos.stream()
                .map(this::toEntityWithoutUser)
                .collect(Collectors.toSet());
    }

    public ParticipantDto toDtoWithoutUser(Participant entity) {
        return ParticipantDto.builder()
                .id(entity.getId())
                .event(new EventMapper().toDtoWithoutOwner(entity.getEvent()))
                .build();
    }

    public Participant toEntityWithoutUser(ParticipantDto dto) {
        return Participant.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .event(new EventMapper().toEntityWithoutOwner(dto.getEvent()))
                .build();
    }
}
