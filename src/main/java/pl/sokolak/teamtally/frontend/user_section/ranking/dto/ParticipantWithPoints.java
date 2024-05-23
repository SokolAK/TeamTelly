package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record ParticipantWithPoints(Integer id,
                                    String username,
                                    String firstName,
                                    String lastName,
                                    String jobTitle,
                                    byte[] photo,
                                    String teamIcon,
                                    String teamName,
                                    String teamColor,
                                    Set<ChallengeDto> completedChallenges,
                                    int points) {

    public ParticipantWithPoints(ParticipantDto participant, int points) {
        this(participant.getId(),
                Optional.ofNullable(participant.getUser()).map(UserDto::getUsername).orElse(null),
                Optional.ofNullable(participant.getUser()).map(UserDto::getFirstName).orElse(null),
                Optional.ofNullable(participant.getUser()).map(UserDto::getLastName).orElse(null),
                Optional.ofNullable(participant.getUser()).map(UserDto::getJobTitle).orElse(null),
                Optional.ofNullable(participant.getUser()).map(UserDto::getPhoto).orElse(null),
                Optional.ofNullable(participant.getTeam()).map(TeamDto::getIcon).orElse(null),
                Optional.ofNullable(participant.getTeam()).map(TeamDto::getName).orElse(null),
                Optional.ofNullable(participant.getTeam()).map(TeamDto::getColor).orElse(null),
                new HashSet<>(participant.getCompletedChallenges()),
                points
        );
    }

//    public ParticipantWithPoints(ParticipantRankingView participant, Set<ChallengeDto> participantChallenges, int points) {
//        this(participant.getUsername(),
//                participant.getFirstName(),
//                participant.getLastName(),
//                participant.getJobTitle(),
//                participant.getPhoto(),
//                participant.getTeamIcon(),
//                participant.g(),
//                participant.getColor(),
//                participantChallenges,
//                points
//        );
//    }
}
