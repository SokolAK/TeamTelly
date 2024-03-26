package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;

public class IndividualRankingRenderer {
    public static Renderer<ParticipantWithPlace> createPlaces() {
        return LitRenderer.<ParticipantWithPlace>of("""
                        <h5 style='text-align: center'>
                            ${item.points > 0 && item.place == 1 ? '\uD83E\uDD47' :
                            item.points > 0 && item.place == 2 ? '\uD83E\uDD48' :
                            item.points > 0 && item.place == 3 ? '\uD83E\uDD49' :
                            item.place}
                        </h5>""")
                .withProperty("points", ParticipantWithPlace::points)
                .withProperty("place", ParticipantWithPlace::place);
    }

    public static Renderer<ParticipantWithPlace> createParticipants() {
        return LitRenderer.<ParticipantWithPlace>of("""
                        <vaadin-vertical-layout>
                            <span><b>${item.firstName} ${item.lastName}</b> <i>${item.username}</i></span>
                            <span style='color: ${item.teamColor}'>${item.teamIcon} ${item.teamName}</span>
                        </vaadin-vertical-layout>""")
                .withProperty("username", ParticipantWithPlace::username)
                .withProperty("firstName", ParticipantWithPlace::firstName)
                .withProperty("lastName", ParticipantWithPlace::lastName)
                .withProperty("teamIcon", ParticipantWithPlace::teamIcon)
                .withProperty("teamName", ParticipantWithPlace::teamName)
                .withProperty("teamColor", ParticipantWithPlace::teamColor);
    }

    public static Renderer<ParticipantWithPlace> createPoints() {
        return LitRenderer.<ParticipantWithPlace>of("<span><b>${item.points}</b></span>")
                .withProperty("points", ParticipantWithPlace::points);
    }
}
