package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.tag.TagDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParticipantRenderer {
    public static Renderer<ParticipantDto> create() {
        return LitRenderer.<ParticipantDto>of("<vaadin-vertical-layout>"
                        + "<h4>${item.lastName} ${item.firstName}</h4>"
                        + "<h7>${item.email}</h7>")
                .withProperty("firstName", p -> p.getUser().getFirstName())
                .withProperty("lastName", p -> p.getUser().getLastName())
                .withProperty("email", p -> p.getUser().getEmail());
    }
}
