package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantChallengeRankingView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPoints;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final ParticipantService participantService;
    private final ChallengeService challengeService;
    private final TeamService teamService;
    private final PointsCalculator pointsCalculator;

    private EventDto event;
    private Set<ParticipantDto> participants;
    private Set<ParticipantChallengeRankingView> completedChallenges;
    private Set<ChallengeDto> challenges;
    private Set<ParticipantWithPoints> participantsWithPoints;
    @Getter
    private Set<ParticipantWithPlace> participantsWithPlaces;
    @Getter
    private Set<TeamDto> teams;


    public void init(EventDto event) {
        this.event = event;
        participants = fetchParticipantsWithoutChallenges();
        completedChallenges = fetchCompletedChallenges();
        challenges = fetchChallenges();
        updateParticipantChallenges();
        teams = fetchTeams();
        updateParticipantTeams();
        participantsWithPoints = createParticipantsWithPoints();
        participantsWithPlaces = createParticipantsWithPlaces();

    }

    private Set<ParticipantDto> fetchParticipantsWithoutChallenges() {
        return participantService.findAllActiveForRankingByEvent(event).stream()
                .map(p -> ParticipantDto.builder()
                        .id(p.getId())
                        .user(UserDto.builder()
                                .username(p.getUsername())
                                .firstName(p.getFirstName())
                                .lastName(p.getLastName())
                                .jobTitle(p.getJobTitle())
                                .photo(p.getPhoto())
                                .build())
                        .team(TeamDto.builder()
                                .id(p.getTeamId())
                                .build())
                        .build()
                ).collect(Collectors.toSet());
    }

    private Set<ParticipantChallengeRankingView> fetchCompletedChallenges() {
        return participantService.findParticipantChallengesForRankingByEvent(event);
    }

    private Set<ChallengeDto> fetchChallenges() {
        Set<Integer> challengeIds = completedChallenges.stream()
                .map(ParticipantChallengeRankingView::getChallengeId) // Extract challengeId
                .collect(Collectors.toSet());
        return challengeService.findAllForRankingByIdIn(challengeIds).stream()
                .map(c -> ChallengeDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .individualPoints(c.getIndividualPoints())
                        .teamPoints(c.getTeamPoints())
                        .build())
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
        return teamService.findAllByEvent(event).stream()
                .map(t -> TeamDto.builder()
                        .id(t.getId())
                        .name(t.getName())
                        .icon(t.getIcon())
                        .color(t.getColor())
                        .participants(new HashSet<>())
                        .build())
                .collect(Collectors.toSet());
    }

    private void updateParticipantTeams() {
        for (ParticipantDto participant : participants) {
            teams.stream()
                    .filter(t -> t.getId().equals(participant.getTeam().getId()))
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
}
