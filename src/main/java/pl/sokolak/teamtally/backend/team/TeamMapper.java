package pl.sokolak.teamtally.backend.team;

import jakarta.transaction.Transactional;
import pl.sokolak.teamtally.backend.event.EventMapper;
import pl.sokolak.teamtally.backend.participant.ParticipantMapper;

import java.util.UUID;

@Transactional
public class TeamMapper {
    public TeamDto toDto(Team entity) {
        if (entity == null) return null;
        return TeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .icon(entity.getIcon())
                .event(new EventMapper().toDto(entity.getEvent()))
                .participants(new ParticipantMapper().toDtosWithoutUser(entity.getParticipants()))
                .build();
    }

    public Team toEntity(TeamDto dto) {
        if (dto == null) return null;
        return Team.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .color(dto.getColor())
                .icon(dto.getIcon())
                .event(new EventMapper().toEntity(dto.getEvent()))
                .participants(new ParticipantMapper().toEntitiesWithoutUser(dto.getParticipants()))
                .build();
    }

    public TeamDto toDtoWithoutEvent(Team entity) {
        if (entity == null) return null;
        return TeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .color(entity.getColor())
                .icon(entity.getIcon())
                .participants(new ParticipantMapper().toDtosWithoutUser(entity.getParticipants()))
                .build();
    }

    public Team toEntityWithoutEvent(TeamDto dto) {
        if (dto == null) return null;
        return Team.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .color(dto.getColor())
                .icon(dto.getIcon())
                .participants(new ParticipantMapper().toEntitiesWithoutUser(dto.getParticipants()))
                .build();
    }
}
