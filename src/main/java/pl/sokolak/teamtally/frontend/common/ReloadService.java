package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import pl.sokolak.teamtally.frontend.MainView;

import java.util.Optional;
import java.util.function.Function;

public class ReloadService {
    public static void reloadAppLayout() {
        Optional.ofNullable(UI.getCurrent())
                .map(UI::getCurrentView)
                .map(Component::getParent)
                .flatMap(Function.identity())
                .map(MainView.class::cast)
                .ifPresent(MainView::reload);
    }
}
