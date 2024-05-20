package pl.sokolak.teamtally.frontend.mainlayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.util.ImageUtil;

@AllArgsConstructor
public class HeaderFactory {

    private final SessionService sessionService;
    private final SecurityService securityService;

    public Component create(Component viewTitle) {
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
//        var headerLeft = new HorizontalLayout(new DrawerToggle(), viewTitle);
        var headerLeft = new HorizontalLayout(new DrawerToggle());
        headerLeft.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        var headerRight = new HorizontalLayout(createPoints(), createUserPhoto(), createLogoutButton());
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRight.addClassName("header-right");

        var header = new HorizontalLayout(headerLeft, headerRight);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        return header;
    }

    private Component createPoints() {
        int points = new PointsCalculator().calculate(sessionService.getParticipant());
        return new Span("⭐" + points);
    }

    private Image createUserPhoto() {
        return ImageUtil.createUserPhotoAsImageSmall(sessionService.getUser().getPhoto());
    }

    private Component createUserNameField() {
        return new H5(sessionService.getUser().getUsername());
    }

    private Button createLogoutButton() {
        return new Button(new Icon(VaadinIcon.EXIT_O), e -> securityService.logout());
    }
}
