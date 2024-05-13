package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.*;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.user_section.challenge.ChallengeView;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPoints;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IndividualRankingService {

    private final ParticipantService participantService;
    private final ChallengeService challengeService;
    private final PointsCalculator pointsCalculator;

    public List<ParticipantWithPlace> getParticipantsWithPlaces(EventDto event) {
        long start = System.currentTimeMillis();

        System.out.println("Getting participants");
        Set<ParticipantRankingDto> participants = participantService.findAllActiveByEvent(event);
        System.out.println("Getting completedChallenges");
        Set<ParticipantChallenge> completedChallenges = participantService.findCompletedChallengesForEvent(event);
        Set<Integer> completedChallengeIds = completedChallenges.stream()
                .map(ParticipantChallenge::getChallengeId)
                .collect(Collectors.toSet());
        System.out.println("Getting challenges");
        Map<Integer, ChallengeDto> challenges = challengeService.findAllByIdIn(completedChallengeIds)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ChallengeDto.builder()
                                .name(e.getValue().getName())
                                .individualPoints(e.getValue().getIndividualPoints())
                                .teamPoints(e.getValue().getTeamPoints())
                                .build()
                ));

        List<ParticipantDto> participantsWithChallenges = participants.stream().map(
                p -> {
                    Set<ChallengeDto> participantChallenges = getParticipantChallenges(p, completedChallenges, challenges);
                    return ParticipantDto.builder()
                            .team(TeamDto.builder()
                                    .name(p.getName())
                                    .icon(p.getIcon())
                                    .color(p.getColor())
                                    .build())
                            .user(UserDto.builder()
                                    .username(p.getUsername())
                                    .firstName(p.getFirstName())
                                    .lastName(p.getLastName())
                                    .jobTitle(p.getJobTitle())
                                    .photo(p.getPhoto())
                                    .build())
                            .completedChallenges(participantChallenges)
                            .build();
                }
        ).collect(Collectors.toList());


        List<ParticipantWithPoints> participantsWithPoints = createParticipantsWithPoints(participantsWithChallenges);


        System.out.println(System.currentTimeMillis() - start);

        List<ParticipantWithPlace> participantsWithPlaces = createParticipantsWithPlaces(participantsWithPoints);
        return participantsWithPlaces;
    }

    private Set<ChallengeDto> getParticipantChallenges(ParticipantRankingDto participant,
                                                       Set<ParticipantChallenge> completedChallenges,
                                                       Map<Integer, ChallengeDto> challenges) {
        return completedChallenges.stream()
                .filter(challenge -> participant.getId().equals(challenge.getParticipantId()))
                .map(challenge -> challenges.get(challenge.getChallengeId()))
                .collect(Collectors.toSet());
    }

    public Component create(List<ParticipantWithPlace> participantsWithPlace) {
        Grid<ParticipantWithPlace> grid = new Grid<>(ParticipantWithPlace.class);
        grid.addClassNames("ranking-individual-grid");
        grid.setAllRowsVisible(true);

        grid.setColumns();
        grid.addColumn(IndividualRankingRenderer.createPlaces())
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(IndividualRankingRenderer.createParticipants());
        grid.addColumn(IndividualRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        grid.setItems(participantsWithPlace);
        grid.setItemDetailsRenderer(IndividualDetailsRenderer.create());

        sort(grid);

        return new Div(grid);
    }

    private static void sort(Grid<ParticipantWithPlace> grid) {
        grid.addColumn(ParticipantWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }

    private List<ParticipantWithPoints> createParticipantsWithPoints(List<ParticipantDto> participants) {
        return participants.stream()
                .map(p -> new ParticipantWithPoints(p, pointsCalculator.calculate(p)))
                .toList();
    }

    private static List<ParticipantWithPlace> createParticipantsWithPlaces(List<ParticipantWithPoints> participantsWithPoints) {
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
                .toList();
    }
}
