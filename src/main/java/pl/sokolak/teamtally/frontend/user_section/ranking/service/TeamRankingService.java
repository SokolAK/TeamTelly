package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPoints;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class TeamRankingService {

    private final TeamService teamService;
    private final PointsCalculator pointsCalculator;

    public List<TeamWithPlace> getTeamsWithPlaces(EventDto event) {
        List<TeamDto> teams = teamService.findAllByEvent(event);
        List<TeamWithPoints> teamsWithPoints = createTeamsWithPoints(teams);
        return createTeamsWithPlaces(teamsWithPoints);
    }

    private List<TeamWithPoints> createTeamsWithPoints(List<TeamDto> teams) {
        return teams.stream()
                .map(team -> new TeamWithPoints(team, pointsCalculator.calculate(team)))
                .toList();
    }

    private static List<TeamWithPlace> createTeamsWithPlaces(List<TeamWithPoints> teamWithPoints) {
        AtomicInteger currentPlace = new AtomicInteger(1);
        AtomicInteger previousTeamPoints = new AtomicInteger();
        return teamWithPoints.stream()
                .sorted(Comparator.comparingInt(TeamWithPoints::points).reversed())
                .map(team -> {
                    if (team.points() < previousTeamPoints.get()) {
                        currentPlace.getAndIncrement();
                    }
                    previousTeamPoints.set(team.points());
                    return new TeamWithPlace(team, currentPlace.get());
                })
                .toList();
    }
}
