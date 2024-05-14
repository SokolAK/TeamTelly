//package pl.sokolak.teamtally.frontend.user_section.ranking.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
//import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
//import pl.sokolak.teamtally.backend.challenge.ChallengeService;
//import pl.sokolak.teamtally.backend.event.EventDto;
//import pl.sokolak.teamtally.backend.participant.ParticipantChallengeRankingView;
//import pl.sokolak.teamtally.backend.participant.ParticipantDto;
//import pl.sokolak.teamtally.backend.participant.ParticipantRankingView;
//import pl.sokolak.teamtally.backend.participant.ParticipantService;
//import pl.sokolak.teamtally.backend.team.TeamDto;
//import pl.sokolak.teamtally.backend.user.UserDto;
//import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
//import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPoints;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class IndividualRankingService {
//
//    private final ParticipantService participantService;
//    private final ChallengeService challengeService;
//    private final PointsCalculator pointsCalculator;
//
//    public List<ParticipantWithPlace> getParticipantsWithPlaces(EventDto event) {
//        Set<ParticipantRankingView> participants = participantService.findAllActiveByEvent(event);
//        Set<ParticipantChallengeRankingView> participantsToChallenges = participantService.findParticipantChallengesForEvent(event);
//        Set<Integer> challengeIds = participantsToChallenges.stream()
//                .map(ParticipantChallengeRankingView::getChallengeId)
//                .collect(Collectors.toSet());
//        Map<Integer, ChallengeDto> challengeIdToChallengeMap = createChallengeIdToChallengeMap(challengeIds);
//        List<ParticipantDto> participantsWithChallenges = createParticipantDtos(participants, participantsToChallenges, challengeIdToChallengeMap);
//        List<ParticipantWithPoints> participantsWithPoints = createParticipantsWithPoints(participantsWithChallenges);
//        return createParticipantsWithPlaces(participantsWithPoints);
//    }
//
//    private List<ParticipantDto> createParticipantDtos(Set<ParticipantRankingView> participants,
//                                                       Set<ParticipantChallengeRankingView> participantsToChallenges,
//                                                       Map<Integer, ChallengeDto> challengeIdToChallengeMap) {
//        return participants.stream()
//                .map(participant -> createParticipantDto(participant, participantsToChallenges, challengeIdToChallengeMap))
//                .collect(Collectors.toList());
//    }
//
//    private ParticipantDto createParticipantDto(ParticipantRankingView participant,
//                                                Set<ParticipantChallengeRankingView> participantsToChallenges,
//                                                Map<Integer, ChallengeDto> challengeIdToChallengeMap) {
//        return ParticipantDto.builder()
//                .team(TeamDto.builder()
//                        .name(participant.getName())
//                        .icon(participant.getIcon())
//                        .color(participant.getColor())
//                        .build())
//                .user(UserDto.builder()
//                        .username(participant.getUsername())
//                        .firstName(participant.getFirstName())
//                        .lastName(participant.getLastName())
//                        .jobTitle(participant.getJobTitle())
//                        .photo(participant.getPhoto())
//                        .build())
//                .completedChallenges(getParticipantChallenges(participant, participantsToChallenges, challengeIdToChallengeMap))
//                .build();
//    }
//
//    private Map<Integer, ChallengeDto> createChallengeIdToChallengeMap(Set<Integer> completedChallengeIds) {
//        return challengeService.findAllByIdIn(completedChallengeIds)
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        e -> ChallengeDto.builder()
//                                .id(e.getValue().getId())
//                                .name(e.getValue().getName())
//                                .individualPoints(e.getValue().getIndividualPoints())
//                                .teamPoints(e.getValue().getTeamPoints())
//                                .build()
//                ));
//    }
//
//    private Set<ChallengeDto> getParticipantChallenges(ParticipantRankingView participant,
//                                                       Set<ParticipantChallengeRankingView> participantsToChallenges,
//                                                       Map<Integer, ChallengeDto> challengeIdToChallengeMap) {
//        return participantsToChallenges.stream()
//                .filter(challenge -> participant.getId().equals(challenge.getParticipantId()))
//                .map(challenge -> challengeIdToChallengeMap.get(challenge.getChallengeId()))
//                .collect(Collectors.toSet());
//    }
//
//    private List<ParticipantWithPoints> createParticipantsWithPoints(List<ParticipantDto> participants) {
//        return participants.stream()
//                .map(p -> new ParticipantWithPoints(p, pointsCalculator.calculate(p)))
//                .toList();
//    }
//
//    private static List<ParticipantWithPlace> createParticipantsWithPlaces(List<ParticipantWithPoints> participantsWithPoints) {
//        AtomicInteger currentPlace = new AtomicInteger(1);
//        AtomicInteger previousParticipantPoints = new AtomicInteger();
//        return participantsWithPoints.stream()
//                .sorted(Comparator.comparingInt(ParticipantWithPoints::points).reversed())
//                .map(participant -> {
//                    if (participant.points() < previousParticipantPoints.get()) {
//                        currentPlace.getAndIncrement();
//                    }
//                    previousParticipantPoints.set(participant.points());
//                    return new ParticipantWithPlace(participant, currentPlace.get());
//                })
//                .toList();
//    }
//}
