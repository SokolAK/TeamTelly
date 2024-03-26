package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;

import java.util.Comparator;
import java.util.List;

public class TeamDetailsRenderer {
    public static ComponentRenderer<VerticalLayout, TeamWithPlace> create(List<ParticipantWithPlace> participantsWithPlace) {
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
                horizontalLayout.add(new Span(member.firstName() + " " + member.lastName()));
                horizontalLayout.add(new Span(String.valueOf(member.points())));
                verticalLayout.add(horizontalLayout);
            }
            if (verticalLayout.getComponentCount() == 0) {
                verticalLayout.add(new Span("No members"));
            }
            return verticalLayout;
        });
    }
}
