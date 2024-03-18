package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.tag.TagDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventRenderer {
    public static Renderer<EventDto> create() {
        return LitRenderer.<EventDto>of("<vaadin-vertical-layout>"
                        + "<h4>${item.name}</h4>"
                        + "<h7>${item.startDate} - ${item.endDate}</h7>"
                        + "<h7>${item.owner}</h7>"
                        + "</vaadin-vertical-layout>")
                .withProperty("name", EventDto::getName)
                .withProperty("owner", e -> e.getOwner().getEmail())
                .withProperty("startDate", e -> e.getStartDate().toString())
                .withProperty("endDate", e -> e.getEndDate().toString());
    }
}
