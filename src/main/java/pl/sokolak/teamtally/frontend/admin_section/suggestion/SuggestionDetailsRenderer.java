package pl.sokolak.teamtally.frontend.admin_section.suggestion;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.util.ImageUtil;

import java.util.Optional;

public class SuggestionDetailsRenderer {
    public static Renderer<SuggestionDto> create() {
        return LitRenderer.<SuggestionDto>of("""
                        <span style='text-align:justify; display:inline-block;'>${item.text}</span>
                        <span><br><br></span>
                        """)
                .withProperty("text", SuggestionDto::getText);
    }
}
