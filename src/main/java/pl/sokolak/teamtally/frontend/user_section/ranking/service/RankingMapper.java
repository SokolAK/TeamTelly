package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ChallengeDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDataView;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.HashSet;

public class RankingMapper {

    public static ParticipantDto map(ParticipantDataView p) {
        return ParticipantDto.builder()
                .id(p.getId())
                .active(true)
                .user(UserDto.builder()
                        .username(p.getUsername())
                        .firstName(p.getFirstName())
                        .lastName(p.getLastName())
                        .jobTitle(p.getJobTitle())
                        .photo(p.getPhoto())
                        .build())
                .team(TeamDto.builder()
                        .id(p.getTeamId())
                        .build())
                .build();
    }

    public static ChallengeDto map(ChallengeDataView c) {
        return ChallengeDto.builder()
                .id(c.getId())
                .name(c.getName())
                .individualPoints(c.getIndividualPoints())
                .teamPoints(c.getTeamPoints())
                .build();
    }

    public static TeamDto map(TeamDataView t) {
        return TeamDto.builder()
                .id(t.getId())
                .name(t.getName())
                .icon(t.getIcon())
                .color(t.getColor())
                .participants(new HashSet<>())
                .build();
    }
}
