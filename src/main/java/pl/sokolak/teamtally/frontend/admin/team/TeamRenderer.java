package pl.sokolak.teamtally.frontend.admin.team;

import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.team.TeamDto;

public class TeamRenderer {
    public static Renderer<TeamDto> create() {
        return LitRenderer.<TeamDto>of("<vaadin-horizontal-layout>"
                        + "<h5 style='color: ${item.color}'>${item.icon} ${item.name}</h4>"
                        + "</vaadin-horizontal-layout>")
                .withProperty("name", TeamDto::getName)
                .withProperty("color", TeamDto::getColor)
                .withProperty("icon", TeamDto::getIcon);
    }
}
