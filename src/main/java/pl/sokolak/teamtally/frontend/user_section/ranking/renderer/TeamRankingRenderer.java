package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.ParticipantWithPoints;
import pl.sokolak.teamtally.frontend.user_section.ranking.TeamWithPoints;

public class TeamRankingRenderer {
    public static Renderer<TeamWithPoints> createPlaces() {
        return LitRenderer.<TeamWithPoints>of("<h5 style='text-align: center'>" +
                                "${index == 0 ? '\uD83E\uDD47' : " +
                                "index == 1 ? '\uD83E\uDD48' : " +
                                "index == 2 ? '\uD83E\uDD49' : " +
                                "index + 1}" +
                                "</h5>")
                .withProperty("points", TeamWithPoints::points);
    }

    public static Renderer<TeamWithPoints> createParticipants() {
        return LitRenderer.<TeamWithPoints>of("<vaadin-horizontal-layout>"
                        + "<h4 style='color: ${item.color}'><b>${item.icon} ${item.name}</b></h4>"
                        + "</vaadin-horizontal-layout>")
                .withProperty("name", TeamWithPoints::name)
                .withProperty("icon", TeamWithPoints::icon)
                .withProperty("color", TeamWithPoints::color);
    }

    public static Renderer<TeamWithPoints> createPoints() {
        return LitRenderer.<TeamWithPoints>of("<span><b>${item.points}</b></span>")
                .withProperty("points", TeamWithPoints::points);
    }
}
