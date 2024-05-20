package pl.sokolak.teamtally.backend.calculator;

import org.junit.jupiter.api.Test;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PointsCalculatorTest {

    private PointsCalculator pointsCalculator = new PointsCalculator();

    @Test
    void shouldCalculateParticipantPoints() {
        int points = pointsCalculator.calculate(participant());
        assertEquals(60, points);
    }

    @Test
    void shouldCalculateTeamPoints() {
        BigDecimal points = pointsCalculator.calculate(teamDto());
        assertEquals(new BigDecimal("316.67"), points);
    }

    private TeamDto teamDto() {
        return TeamDto.builder()
                .id(1)
                .participants(Stream.of(participant(), participant2(), participant3(), participant4()).collect(Collectors.toSet()))
                .build();
    }

    private ParticipantDto participant() {
        Set<ChallengeDto> challenges = new HashSet<>();
        challenges.add(challenge(1, 10, 100));
        challenges.add(challenge(2, 20, 200));
        challenges.add(challenge(3, 30, 300));
        return ParticipantDto.builder()
                .id(1)
                .active(true)
                .completedChallenges(challenges)
                .team(TeamDto.builder()
                        .id(1)
                        .build())
                .build();
    }

    private ParticipantDto participant2() {
        Set<ChallengeDto> challenges = new HashSet<>();
        challenges.add(challenge(4, 2, 200));
        challenges.add(challenge(5, 3, 300));
        return ParticipantDto.builder()
                .id(2)
                .active(true)
                .completedChallenges(challenges)
                .team(TeamDto.builder()
                        .id(1)
                        .build())
                .build();
    }

    private ParticipantDto participant3() {
        Set<ChallengeDto> challenges = new HashSet<>();
        challenges.add(challenge(1, 10, 100));
        challenges.add(challenge(2, 20, 200));
        return ParticipantDto.builder()
                .id(3)
                .active(true)
                .completedChallenges(challenges)
                .team(TeamDto.builder()
                        .id(1)
                        .build())
                .build();
    }

    private ParticipantDto participant4() {
        Set<ChallengeDto> challenges = new HashSet<>();
        challenges.add(challenge(1, 10, 100));
        challenges.add(challenge(2, 20, 200));
        return ParticipantDto.builder()
                .id(4)
                .active(false)
                .completedChallenges(challenges)
                .team(TeamDto.builder()
                        .id(1)
                        .build())
                .build();
    }

    private ChallengeDto challenge(int id, int individualPoints, int teamPoints) {
        return ChallengeDto.builder()
                .id(id)
                .individualPoints(individualPoints)
                .teamPoints(teamPoints)
                .build();
    }
}
