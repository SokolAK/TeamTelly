package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;

public class IndividualDetailsRenderer {
    public static ComponentRenderer<VerticalLayout, ParticipantWithPlace> create() {
        return new ComponentRenderer<>(p -> {
            VerticalLayout verticalLayout = new VerticalLayout();
            for (ChallengeDto completedChallenge : p.completedChallenges()) {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
                horizontalLayout.setWidthFull();
                Span challengeName = new Span(completedChallenge.getName());
                challengeName.getStyle().set("font-size", "medium");
                Span points = new Span("⭐ " + completedChallenge.getIndividualPoints());
                points.getStyle().set("font-size", "medium");
                points.getStyle().set("white-space", "nowrap");
                horizontalLayout.add(challengeName);
                horizontalLayout.add(points);
                verticalLayout.add(horizontalLayout);
                verticalLayout.getStyle()
                        .set("padding-top", "8px")
                        .set("padding-left", "4px")
                        .set("padding-right", "4px")
                        .set("padding-bottom", "12px");
            }
            if (verticalLayout.getComponentCount() == 0) {
                verticalLayout.add(new Span("No challenge completed"));
            }
            return verticalLayout;
        });
    }
}
