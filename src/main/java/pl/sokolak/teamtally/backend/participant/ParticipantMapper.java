package pl.sokolak.teamtally.backend.participant;

import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.user.UserMapper;

import java.util.UUID;

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
}
