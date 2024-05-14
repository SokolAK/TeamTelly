package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ParticipantChallengeRankingView {
    private Integer participantId;
    private Integer challengeId;
}
