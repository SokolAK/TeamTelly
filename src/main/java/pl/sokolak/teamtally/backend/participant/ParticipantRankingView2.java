package pl.sokolak.teamtally.backend.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ParticipantRankingView2 {
    UserRankingView user;
    TeamRankingView team;
    Set<Integer> completedChallenges;
}
