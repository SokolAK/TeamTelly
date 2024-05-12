package pl.sokolak.teamtally.backend.participant.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ParticipantRankingView {
    UserRankingView user;
    TeamRankingView team;
    Set<ChallengeRankingView> completedChallenges;
}
