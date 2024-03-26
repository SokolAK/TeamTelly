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
                horizontalLayout.add(new Span(completedChallenge.getName()));
                horizontalLayout.add(new Span(String.valueOf(completedChallenge.getIndividualPoints())));
                verticalLayout.add(horizontalLayout);
            }
            if (verticalLayout.getComponentCount() == 0) {
                verticalLayout.add(new Span("No challenge completed"));
            }
            return verticalLayout;
        });
    }
}
