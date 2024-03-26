package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPoints;

public class TeamRankingRenderer {
    public static Renderer<TeamWithPlace> createPlaces() {
        return LitRenderer.<TeamWithPlace>of("""
                                <h5 style='text-align: center'>
                                    ${index == 0 ? '\uD83E\uDD47' :
                                    index == 1 ? '\uD83E\uDD48' :
                                    index == 2 ? '\uD83E\uDD49' :
                                    index + 1}
                                </h5>""")
                .withProperty("points", TeamWithPlace::points);
    }

    public static Renderer<TeamWithPlace> createTeams() {
        return LitRenderer.<TeamWithPlace>of("""
                        <vaadin-horizontal-layout>
                            <span style='color: ${item.color}'><b>${item.icon} ${item.name}</b></span>
                        </vaadin-horizontal-layout>""")
                .withProperty("name", TeamWithPlace::name)
                .withProperty("icon", TeamWithPlace::icon)
                .withProperty("color", TeamWithPlace::color);
    }

    public static Renderer<TeamWithPlace> createPoints() {
        return LitRenderer.<TeamWithPlace>of("<span><b>${item.points}</b></span>")
                .withProperty("points", TeamWithPlace::points);
    }
}
