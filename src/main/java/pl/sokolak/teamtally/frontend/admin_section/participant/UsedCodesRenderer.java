package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;

public class UsedCodesRenderer {
    public static Renderer<ParticipantDto> create() {
        return new ComponentRenderer<>(p -> {
            VerticalLayout verticalLayout = new VerticalLayout();
            for (CodeDto usedCode : p.getUsedCodes()) {
                verticalLayout.add(new Span(usedCode.getCode()));
            }
            if (verticalLayout.getComponentCount() == 0) {
                verticalLayout.add(new Span("No code used"));
            }
            return verticalLayout;
        });
    }
}
