package pl.sokolak.teamtally.backend.participant;

import pl.sokolak.teamtally.backend.challenge.Challenge;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.User;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.Set;

public interface ParticipantRankingView {
    UserRankingView getUser();
    TeamRankingView getTeam();
    Set<ChallengeRankingView> getCompletedChallenges();
}
