package pl.sokolak.teamtally.frontend.user_section.ranking.renderer;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.util.ImageUtil;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;

public class IndividualRankingRenderer {
    public static Renderer<ParticipantWithPlace> createPlaces() {
        return LitRenderer.<ParticipantWithPlace>of("""
                        <span style='text-align:center; font-size:${item.points > 0 & item.place <= 3 ? "x-large" : "large"}'>
                            ${item.points > 0 && item.place == 1 ? '\uD83E\uDD47' :
                            item.points > 0 && item.place == 2 ? '\uD83E\uDD48' :
                            item.points > 0 && item.place == 3 ? '\uD83E\uDD49' :
                            item.place}
                        </span>""")
                .withProperty("points", ParticipantWithPlace::points)
                .withProperty("place", ParticipantWithPlace::place);
    }

    public static Renderer<ParticipantWithPlace> createParticipants() {
        return LitRenderer.<ParticipantWithPlace>of("""
                        <vaadin-horizontal-layout>
                            <div class='user-photo-div'>
                                <img class='user-photo-medium' src=${item.photo} alt=${item.name}>
                            </div>
                            <vaadin-vertical-layout style='margin-left: 10px'>
                                <span style='text-wrap:balance; font-size:15px'><b>${item.firstName} ${item.lastName}</b></span>
                                <span style='text-wrap:balance; font-size:small'>${item.jobTitle}</span>
                                <span style='color: ${item.teamColor}; font-size:small'>${item.teamIcon} ${item.teamName}</span>
                            </vaadin-vertical-layout>
                        </vaadin-horizontal-layout>
                        """)
                .withProperty("username", ParticipantWithPlace::username)
                .withProperty("firstName", ParticipantWithPlace::firstName)
                .withProperty("lastName", ParticipantWithPlace::lastName)
                .withProperty("jobTitle", ParticipantWithPlace::jobTitle)
                .withProperty("teamIcon", ParticipantWithPlace::teamIcon)
                .withProperty("teamName", ParticipantWithPlace::teamName)
                .withProperty("teamColor", ParticipantWithPlace::teamColor)
                .withProperty("photo", p -> ImageUtil.createUserPhotoAsBase64(p.photo()));
    }

    public static Renderer<ParticipantWithPlace> createPoints() {
        return LitRenderer.<ParticipantWithPlace>of("<h6>⭐ ${item.points}</h6>")
                .withProperty("points", ParticipantWithPlace::points);
    }
}
