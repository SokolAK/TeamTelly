package pl.sokolak.teamtally.frontend.admin_section.team;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

import java.util.List;

class TeamForm extends AbstractForm {

    private final TextField name = new TextField("Name");
    private final ComboBox<String> color = new ComboBox<>("Color");
    private final ComboBox<String> icon = new ComboBox<>("Icon");

    public TeamForm() {
        addClassName("team-form");
        configureBinder();
        color.setRenderer(createColorRenderer());
        color.setItems(createColors());
        icon.setRenderer(createIconRenderer());
        icon.setItems(createIcons());
        addFields(name, color, icon);
    }

    private void configureBinder() {
        Binder<TeamDto> binder = new BeanValidationBinder<>(TeamDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }

    private List<String> createColors() {
        return List.of("#e69138", "#c90076", "#6aa84f", "#2986cc");
    }

    private Renderer<String> createColorRenderer() {
        return LitRenderer.<String> of(
                "<span style='background-color:${item.color}'>&nbsp; &nbsp; &nbsp; &nbsp; </span>")
                .withProperty("color", ValueProvider.identity());
    }

    private List<String> createIcons() {
        return List.of("\uD83D\uDC3F", "\uD83D\uDC2C", "\uD83D\uDC1D");
    }

    private Renderer<String> createIconRenderer() {
        return LitRenderer.<String> of(
                        "<span>${item.icon}</span>")
                .withProperty("icon", ValueProvider.identity());
    }
}
