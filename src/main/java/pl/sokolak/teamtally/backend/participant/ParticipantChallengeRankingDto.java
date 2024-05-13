package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantChallengeRankingDto {
    private Integer participantId;
    private Integer challengeId;
}
