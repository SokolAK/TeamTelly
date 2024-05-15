package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.*;
import pl.sokolak.teamtally.backend.team.TeamDataView;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPoints;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPoints;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final ParticipantService participantService;
    private final ChallengeService challengeService;
    private final TeamService teamService;
    private final PointsCalculator pointsCalculator;

    @Getter
    private Set<ParticipantWithPlace> participantsWithPlaces;
    @Getter
    private Set<TeamWithPlace> teamsWithPlaces;

    private EventDto event;
    private Set<ParticipantDto> participants;
    private Set<ParticipantChallengeRankingView> completedChallenges;
    private Set<ChallengeDto> challenges;
    private Set<ParticipantWithPoints> participantsWithPoints;
    private Set<TeamDto> teams;


    public void init(EventDto event) {
        this.event = event;
        long start = System.currentTimeMillis();
        participants = fetchParticipantsWithoutChallenges();
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        completedChallenges = fetchCompletedChallenges();
        challenges = fetchChallenges();
        updateParticipantChallenges();
        teams = fetchTeams();
        updateParticipantTeams();
        participantsWithPoints = createParticipantsWithPoints();
        participantsWithPlaces = createParticipantsWithPlaces();
        Set<TeamWithPoints> teamsWithPoints = createTeamsWithPoints(teams);
        teamsWithPlaces = createTeamsWithPlaces(teamsWithPoints);
    }

    private Set<ParticipantDto> fetchParticipantsWithoutChallenges() {
        return participantService.findAllActiveDataByEvent(event).stream()
                .map(RankingMapper::map)
                .collect(Collectors.toSet());
    }

    private Set<ParticipantChallengeRankingView> fetchCompletedChallenges() {
        return participantService.findParticipantChallengesForRankingByEvent(event);
    }

    private Set<ChallengeDto> fetchChallenges() {
        Set<Integer> challengeIds = completedChallenges.stream()
                .map(ParticipantChallengeRankingView::getChallengeId) // Extract challengeId
                .collect(Collectors.toSet());
        return challengeService.findAllForRankingByIdIn(challengeIds).stream()
                .map(RankingMapper::map)
                .collect(Collectors.toSet());
    }

    private void updateParticipantChallenges() {
        for (ParticipantDto participant : participants) {
            Set<ChallengeDto> participantChallenges = completedChallenges.stream()
                    .filter(cc -> participant.getId().equals(cc.getParticipantId()))
                    .map(cc -> challenges.stream()
                            .filter(c -> c.getId().equals(cc.getChallengeId()))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            participant.setCompletedChallenges(participantChallenges);
        }
    }

    private Set<TeamDto> fetchTeams() {
        return teamService.findAllDataViewByEvent(event).stream()
                .map(RankingMapper::map)
                .collect(Collectors.toSet());
    }

    private void updateParticipantTeams() {
        for (ParticipantDto participant : participants) {
            Integer participantTeamId = Optional.ofNullable(participant.getTeam()).map(Data::getId).orElse(null);
            teams.stream()
                    .filter(t -> t.getId().equals(participantTeamId))
                    .findFirst()
                    .ifPresent(teams -> {
                        teams.getParticipants().add(participant);
                        participant.setTeam(teams);
                    });
        }
    }

    private Set<ParticipantWithPoints> createParticipantsWithPoints() {
        return participants.stream()
                .map(p -> new ParticipantWithPoints(p, pointsCalculator.calculate(p)))
                .collect(Collectors.toSet());
    }

    private Set<ParticipantWithPlace> createParticipantsWithPlaces() {
        AtomicInteger currentPlace = new AtomicInteger(1);
        AtomicInteger previousParticipantPoints = new AtomicInteger();
        return participantsWithPoints.stream()
                .sorted(Comparator.comparingInt(ParticipantWithPoints::points).reversed())
                .map(participant -> {
                    if (participant.points() < previousParticipantPoints.get()) {
                        currentPlace.getAndIncrement();
                    }
                    previousParticipantPoints.set(participant.points());
                    return new ParticipantWithPlace(participant, currentPlace.get());
                })
                .collect(Collectors.toSet());
    }

    private Set<TeamWithPoints> createTeamsWithPoints(Set<TeamDto> teams) {
        return teams.stream()
                .map(team -> new TeamWithPoints(team, pointsCalculator.calculate(team)))
                .collect(Collectors.toSet());
    }

    private Set<TeamWithPlace> createTeamsWithPlaces(Set<TeamWithPoints> teamWithPoints) {
        AtomicInteger currentPlace = new AtomicInteger(1);
        final BigDecimal[] previousTeamPoints = {new BigDecimal(0)};
        return teamWithPoints.stream()
                .sorted(Comparator.comparing(TeamWithPoints::points))
                .map(team -> {
                    if (team.points().compareTo(previousTeamPoints[0]) < 0) {
                        currentPlace.getAndIncrement();
                    }
                    previousTeamPoints[0] = team.points();
                    return new TeamWithPlace(team, currentPlace.get());
                })
                .collect(Collectors.toSet());
    }
}
