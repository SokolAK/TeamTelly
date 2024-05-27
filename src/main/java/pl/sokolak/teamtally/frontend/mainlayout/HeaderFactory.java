package pl.sokolak.teamtally.frontend.mainlayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.util.EventBus;
import pl.sokolak.teamtally.backend.util.ImageUtil;

import java.util.function.Consumer;

@AllArgsConstructor
public class HeaderFactory {

    private final SessionService sessionService;
    private final SecurityService securityService;
    private final EventBus eventBus;
    private final H5 myPointsField = new H5();

    public Component create(Component viewTitle) {
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
//        var headerLeft = new HorizontalLayout(new DrawerToggle(), viewTitle);
        var headerLeft = new HorizontalLayout(new DrawerToggle());
        headerLeft.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        var headerRight = new HorizontalLayout(createUserPhoto(), createLogoutButton());
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRight.addClassName("header-right");

        var header = new HorizontalLayout(headerLeft, headerRight);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

//        myPointsField.setClassName("points-field");
//        myPointsField.setText(createPoints());
//        eventBus.addListener("my-points", points -> myPointsField.setText(printPoints((Integer) points)));

        return header;
    }

    private String createPoints() {
        return printPoints(new PointsCalculator().calculate(sessionService.getParticipant()));
    }

    private String printPoints(Integer points) {
        return "⭐ " + points;
    }

    private Component createUserTeam() {
        TeamDto team = sessionService.getParticipant().getTeam();
        if(team == null) {
            return new Span();
        }
        return new Span(team.getIcon() + " " + team.getName());
    }

    private Image createUserPhoto() {
        Image photo = ImageUtil.createUserPhotoAsImageSmall(sessionService.getUser().getPhoto());
        photo.getStyle().set("cursor", "pointer");
        photo.addClickListener(e -> photo.getUI().ifPresent(ui ->
                ui.navigate("my-profile")));
        return photo;
    }

    private Component createUserNameField() {
        return new H5(sessionService.getUser().getUsername());
    }

    private Button createLogoutButton() {
        return new Button(new Icon(VaadinIcon.EXIT_O), e -> securityService.logout());
    }
}
