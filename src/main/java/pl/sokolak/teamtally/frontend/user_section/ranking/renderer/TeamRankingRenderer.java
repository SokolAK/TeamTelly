package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;

public class TeamRankingRenderer {
    public static Renderer<TeamWithPlace> createPlaces() {
        return LitRenderer.<TeamWithPlace>of("""
                        <span style='text-align: center'>
                            ${item.points > 0 && item.place == 1 ? '\uD83E\uDD47' :
                            item.points > 0 && item.place == 2 ? '\uD83E\uDD48' :
                            item.points > 0 && item.place == 3 ? '\uD83E\uDD49' :
                            item.place}
                        </span>""")
                .withProperty("points", TeamWithPlace::points)
                .withProperty("place", TeamWithPlace::place);
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
        return LitRenderer.<TeamWithPlace>of("<span><b>⭐ ${item.points}</b></span>")
                .withProperty("points", TeamWithPlace::points);
    }
}
