package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationService {

    private final static Position position = Position.TOP_CENTER;
    private final static int duration = 3000;

    public static void showSuccess(String text) {
        show(text, NotificationVariant.LUMO_SUCCESS, VaadinIcon.CHECK_CIRCLE.create());
    }

    public static void showWarning(String text) {
        show(text, NotificationVariant.LUMO_WARNING, VaadinIcon.WARNING.create());
    }

    public static void showError(String text) {
        show(text, NotificationVariant.LUMO_ERROR, VaadinIcon.CLOSE_CIRCLE.create());
    }

    private static void show(String text, NotificationVariant variant, Icon icon) {
        Notification notification = Notification.show(text);
        notification.addThemeVariants(variant);
        notification.setPosition(position);
        notification.setDuration(duration);

        var layout = new HorizontalLayout(icon, new Text(text));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
    }
}
