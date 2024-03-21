package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.ParticipantWithPoints;

public class IndividualRankingRenderer {
    public static Renderer<ParticipantWithPoints> createParticipants() {
        return LitRenderer.<ParticipantWithPoints>of(
                "<vaadin-vertical-layout>"
                        + "<span><b>${item.firstName} ${item.lastName}</b> <i>${item.username}</i></span>"
                        + "<span style='color: ${item.teamColor}'>${item.teamIcon} ${item.teamName}</span>"
                        + "</vaadin-vertical-layout>")
                .withProperty("username", ParticipantWithPoints::username)
                .withProperty("firstName", ParticipantWithPoints::firstName)
                .withProperty("lastName", ParticipantWithPoints::lastName)
                .withProperty("teamIcon", ParticipantWithPoints::teamIcon)
                .withProperty("teamName", ParticipantWithPoints::teamName)
                .withProperty("teamColor", ParticipantWithPoints::teamColor);
    }

    public static Renderer<ParticipantWithPoints> createPoints() {
        return LitRenderer.<ParticipantWithPoints>of("<span><b>${item.points}</b></span>")
                .withProperty("points", ParticipantWithPoints::points);
    }
}
