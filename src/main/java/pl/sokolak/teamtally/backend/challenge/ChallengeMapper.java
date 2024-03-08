package pl.sokolak.teamtally.backend.challenge;

import java.util.UUID;

public class ChallengeMapper {
    public ChallengeDto toDto(Challenge entity) {
        return ChallengeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .personalPoints(entity.getPersonalPoints())
                .teamPoints(entity.getTeamPoints())
                .build();
    }

    public Challenge toEntity(ChallengeDto dto) {
        return Challenge.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .personalPoints(dto.getPersonalPoints())
                .teamPoints(dto.getTeamPoints())
                .build();
    }
}
