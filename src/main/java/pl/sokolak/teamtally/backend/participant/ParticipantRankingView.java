package pl.sokolak.teamtally.backend.participant;

import java.util.Set;

public interface ParticipantRankingView {
    UserRankingView getUser();
    TeamRankingView getTeam();
    Set<ChallengeRankingView> getCompletedChallenges();
}
