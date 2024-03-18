package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;

public class ParticipantRenderer {
    public static Renderer<ParticipantDto> create() {
        return LitRenderer.<ParticipantDto>of("<vaadin-vertical-layout>"
                        + "<h4>${item.lastName} ${item.firstName}</h4>"
                        + "<h7>${item.email}</h7>"
                        + "</vaadin-vertical-layout>")
                .withProperty("firstName", p -> p.getUser().getFirstName())
                .withProperty("lastName", p -> p.getUser().getLastName())
                .withProperty("email", p -> p.getUser().getEmail());
    }
}
