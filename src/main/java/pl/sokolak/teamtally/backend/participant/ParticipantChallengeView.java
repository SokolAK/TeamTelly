package pl.sokolak.teamtally.backend.participant;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sokolak.teamtally.abstracts.AbstractEntityWithEvent;
import pl.sokolak.teamtally.backend.challenge.Challenge;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantChallengeView {
    private Integer participantId;
    private Integer challengeId;
}
