package pl.sokolak.teamtally.backend.participant.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChallengeRankingView {
    String name;
    Integer individualPoints;
    Integer teamPoints;
}
