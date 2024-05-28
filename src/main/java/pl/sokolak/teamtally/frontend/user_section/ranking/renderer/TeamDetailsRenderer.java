package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class  TeamDetailsRenderer {
    public static ComponentRenderer<VerticalLayout, TeamWithPlace> create(Set<ParticipantWithPlace> participantsWithPlace) {
        return new ComponentRenderer<>(team -> {

            VerticalLayout verticalLayout = new VerticalLayout();
            List<ParticipantWithPlace> teamMembers = participantsWithPlace.stream()
                    .filter(participant -> team.name().equals(participant.teamName()))
                    .sorted(Comparator.comparingInt(ParticipantWithPlace::points).reversed())
                    .toList();

            for (ParticipantWithPlace member : teamMembers) {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                horizontalLayout.setWidthFull();
                Span name = new Span("\uD83D\uDC64 " + member.firstName() + " " + member.lastName());
                name.getStyle().set("font-size", "medium");
                Span points = new Span("⭐ " + member.points());
                points.getStyle().set("font-size", "medium");
                points.getStyle().set("white-space", "nowrap");
                horizontalLayout.add(name);
                horizontalLayout.add(points);
                verticalLayout.add(horizontalLayout);
                verticalLayout.getStyle().set("padding", "0");
            }

            for (ChallengeDto completedChallenge : getChallengesCompletedByTeam(teamMembers)) {
                if(completedChallenge.getTeamPoints() > 0) {
                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                    horizontalLayout.setWidthFull();
                    Span bonusName = new Span("\uD83D\uDC65 " + completedChallenge.getName());
                    bonusName.getStyle().set("font-size", "medium");
                    Span points = new Span("⭐ " + completedChallenge.getTeamPoints());
                    points.getStyle().set("font-size", "medium");
                    points.getStyle().set("white-space", "nowrap");
                    horizontalLayout.add(bonusName);
                    horizontalLayout.add(points);
                    verticalLayout.add(horizontalLayout);
                    verticalLayout.getStyle().set("padding", "0");
                }
            }

            if (verticalLayout.getComponentCount() == 0) {
                verticalLayout.add(new Span("No members"));
            }
            return verticalLayout;
        });
    }

    private static List<ChallengeDto> getChallengesCompletedByTeam(List<ParticipantWithPlace> teamMembers) {
        Set<ChallengeDto> challenges = teamMembers.stream()
                .map(ParticipantWithPlace::completedChallenges)
                .min(Comparator.comparingInt(Set::size))
                .orElse(Collections.emptySet());
        return challenges.stream()
                .filter(c -> teamMembers.stream()
                        .map(ParticipantWithPlace::completedChallenges)
                        .allMatch(cs -> cs.contains(c)))
                .toList();
    }
}
