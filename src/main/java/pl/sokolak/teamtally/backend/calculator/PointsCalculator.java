package pl.sokolak.teamtally.backend.calculator;

import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class PointsCalculator {

    public int calculate(ParticipantDto participant) {
        return participant.getCompletedChallenges().stream()
                .map(ChallengeDto::getPersonalPoints)
                .reduce(0, Integer::sum);
    }

    public int calculate(TeamDto team) {
        return sumIndividualPoints(team) + sumBonusPoints(team);
    }

    private int sumIndividualPoints(TeamDto team) {
        return team.getParticipants().stream()
                .map(this::calculate)
                .reduce(0, Integer::sum);
    }

    private int sumBonusPoints(TeamDto team) {

        Set<ChallengeDto> challenges = team.getParticipants().stream()
                .map(ParticipantDto::getCompletedChallenges)
                .min(Comparator.comparingInt(Set::size))
                .orElse(Collections.emptySet());

        return challenges.stream()
                .filter(c -> team.getParticipants().stream()
                        .map(ParticipantDto::getCompletedChallenges)
                        .allMatch(cs -> cs.contains(c)))
                .map(ChallengeDto::getTeamPoints)
                .reduce(0, Integer::sum);
    }
}
