package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.ParticipantWithPoints;

public class PlaceRenderer {
    public static Renderer<ParticipantWithPoints> create() {
        return LitRenderer.<ParticipantWithPoints>of(
                        "<h5 style='text-align: center'>" +
                                "${index == 0 ? '\uD83E\uDD47' : " +
                                "index == 1 ? '\uD83E\uDD48' : " +
                                "index == 2 ? '\uD83E\uDD49' : " +
                                "index + 1}" +
                                "</h5>")
                .withProperty("points", ParticipantWithPoints::points);
    }
}
