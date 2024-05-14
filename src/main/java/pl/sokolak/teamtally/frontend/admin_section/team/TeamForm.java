package pl.sokolak.teamtally.frontend.admin_section.team;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

import java.util.List;

class TeamForm extends SaveDeleteCancelAbstractForm {

    private final TextField name = new TextField("Name");
    private final ComboBox<String> color = new ComboBox<>("Color");
    //    private final ComboBox<String> icon = new ComboBox<>("Icon");
    private final TextField icon = new TextField("Icon");

    public TeamForm() {
        addClassName("team-form");
        configureBinder();
        color.setRenderer(createColorRenderer());
        color.setItems(createColors());
//        icon.setRenderer(createIconRenderer());
//        icon.setItems(createIcons());
        addFields(name, color, icon);
    }

    private void configureBinder() {
        Binder<TeamDto> binder = new BeanValidationBinder<>(TeamDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }

    private List<String> createColors() {
        return List.of(
//                "#e69138", "#c90076", "#6aa84f", "#2986cc"
                "#c0392b", "#e74c3c", "#9b59b6", "#8e44ad", "#2980b9", "#3498db",
                "#1abc9c", "#16a085", "#27ae60", "#2ecc71", "#f1c40f", "#f39c12",
                "#e67e22", "#d35400", "#7f8c8d", "#2c3e50", "#000000"
        );
    }

    private Renderer<String> createColorRenderer() {
        return LitRenderer.<String>of(
                        "<span style='background-color:${item.color}'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>")
                .withProperty("color", ValueProvider.identity());
    }

    private List<String> createIcons() {
        return List.of("\uD83D\uDC3F", "\uD83D\uDC2C", "\uD83D\uDC1D");
    }

    private Renderer<String> createIconRenderer() {
        return LitRenderer.<String>of(
                        "<span>${item.icon}</span>")
                .withProperty("icon", ValueProvider.identity());
    }
}
