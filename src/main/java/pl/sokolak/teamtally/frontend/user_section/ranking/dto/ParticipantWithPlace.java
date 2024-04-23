package pl.sokolak.teamtally.frontend.user_section.ranking.dto;

import pl.sokolak.teamtally.backend.challenge.ChallengeDto;

import java.util.HashSet;
import java.util.Set;

public record ParticipantWithPlace(String username,
                                   String firstName,
                                   String lastName,
                                   String jobTitle,
                                   byte[] photo,
                                   String teamIcon,
                                   String teamName,
                                   String teamColor,
                                   Set<ChallengeDto> completedChallenges,
                                   int points,
                                   int place) {

    public ParticipantWithPlace(ParticipantWithPoints participant, int place) {
        this(participant.username(),
                participant.firstName(),
                participant.lastName(),
                participant.jobTitle(),
                participant.photo(),
                participant.teamIcon(),
                participant.teamName(),
                participant.teamColor(),
                new HashSet<>(participant.completedChallenges()),
                participant.points(),
                place
        );
    }
}
