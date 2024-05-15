package pl.sokolak.teamtally.backend.calculator;

import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@Service
public class PointsCalculator {

    public int calculate(ParticipantDto participant) {
        return participant.getCompletedChallenges().stream()
                .map(ChallengeDto::getIndividualPoints)
                .reduce(0, Integer::sum);
    }

    public BigDecimal calculate(TeamDto team) {
        int numberOfMembers = team.getParticipants().toArray().length;
        if(numberOfMembers == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal averageIndividualPoints = new BigDecimal((double) sumIndividualPoints(team) / numberOfMembers)
                .setScale(2, RoundingMode.HALF_UP);
        return averageIndividualPoints
                .multiply(BigDecimal.TEN)
                .add(BigDecimal.valueOf(sumBonusPoints(team)));
    }

    private int sumIndividualPoints(TeamDto team) {
        if(team.getParticipants() == null) {
            return 0;
        }
        return team.getParticipants().stream()
                .filter(ParticipantDto::isActive)
                .map(this::calculate)
                .reduce(0, Integer::sum);
    }

    private int sumBonusPoints(TeamDto team) {
        if(team.getParticipants() == null) {
            return 0;
        }
        Set<ChallengeDto> challenges = team.getParticipants().stream()
                .filter(ParticipantDto::isActive)
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
