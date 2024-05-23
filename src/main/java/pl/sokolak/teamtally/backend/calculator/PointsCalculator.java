package pl.sokolak.teamtally.backend.calculator;

import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Service
public class PointsCalculator {

    public int calculate(ParticipantDto participant) {
        return participant.getCompletedChallenges().stream()
                .map(ChallengeDto::getIndividualPoints)
                .reduce(0, Integer::sum);
    }

    public BigDecimal calculate(TeamDto team) {
        return BigDecimal.valueOf(sumIndividualPoints(team) + sumBonusPoints(team))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAverage(TeamDto team) {
        long numberOfMembers = team.getParticipants().stream().filter(ParticipantDto::isActive).count();
        if(numberOfMembers == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal averageIndividualPoints = new BigDecimal((double) sumIndividualPoints(team) / numberOfMembers)
                .multiply(BigDecimal.TEN)
                .setScale(2, RoundingMode.HALF_UP);
        return averageIndividualPoints
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
        return team.getParticipants().stream()
                .filter(ParticipantDto::isActive)
                .map(ParticipantDto::getCompletedChallenges)
                .reduce(team.getParticipants().stream().findFirst().get().getCompletedChallenges(), this::retain)
                .stream()
                .map(ChallengeDto::getTeamPoints)
                .reduce(0, Integer::sum);
    }

    private <T> Set<T> retain(Set<T> a, Set<T> b) {
        a.retainAll(b);
        return a;
    }
}
