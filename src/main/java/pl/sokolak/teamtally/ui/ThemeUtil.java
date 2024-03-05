package pl.sokolak.teamtally.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;

public class ThemeUtil {

    public static void setThemeVariant(String variant) {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.clear();
        themeList.add(variant);
    }

    public static void toggleThemeVariant() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (themeList.contains(Lumo.DARK)) {
            themeList.remove(Lumo.DARK);
        } else {
            themeList.add(Lumo.DARK);
        }
    }
}
