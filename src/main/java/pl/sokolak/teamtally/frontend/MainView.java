package pl.sokolak.teamtally.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.admin.challenge.ChallengeView;
import pl.sokolak.teamtally.frontend.admin.event.EventView;
import pl.sokolak.teamtally.frontend.admin.team.TeamView;
import pl.sokolak.teamtally.frontend.admin.user.UserView;
import pl.sokolak.teamtally.frontend.scoreboard.ScoreboardView;

import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class MainView extends AppLayout {

    private final SecurityService securityService;
    private final SessionService sessionService;
    private UserDto user;
    private final H2 viewTitle = new H2();

    public MainView(SecurityService securityService, SessionService sessionService) {
        this.securityService = securityService;
        this.sessionService = sessionService;
        init();
    }

    public void reload() {
        Component content = getContent();
        remove(getChildren().toArray(Component[]::new));
        init();
        setContent(content);
    }

    private void init() {
        sessionService.init();
        this.user = sessionService.getUser();
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addDrawerContent() {
        Scroller scroller = new Scroller(createNavigation());
        Image logo = new Image("assets/logo.png", "Team Tally");
        logo.addClassName("logo-small");

        H1 eventName = new H1(sessionService.getEventName());
        eventName.addClassName("event-name");
        String eventStartDate = Optional.ofNullable(sessionService.getEventStartDate()).map(d -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).orElse("");
        String eventEndDate = Optional.ofNullable(sessionService.getEventEndDate()).map(d -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).orElse("");
        H2 eventDate;
        if (eventStartDate.isEmpty() && eventEndDate.isEmpty()) {
            eventDate = new H2();
        } else {
            eventDate = new H2(eventStartDate + " - " + eventEndDate);
        }
        eventDate.addClassName("event-date");

        addToDrawer(logo, eventName, eventDate, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Scoreboard", ScoreboardView.class, VaadinIcon.TROPHY.create()));

        if (user.isAdmin()) {
            nav.addItem(new SideNavItem(" "));
            nav.addItem(new SideNavItem(" "));
            nav.addItem(new SideNavItem("Admin area"));
            nav.addItem(new SideNavItem("Events", EventView.class, VaadinIcon.STAR.create()));
            nav.addItem(new SideNavItem("Challenges", ChallengeView.class, VaadinIcon.ROCKET.create()));
            nav.addItem(new SideNavItem("Teams", TeamView.class, VaadinIcon.USERS.create()));
            nav.addItem(new SideNavItem("Users", UserView.class, VaadinIcon.USERS.create()));
        }

        return nav;
    }

    private void addHeaderContent() {
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        var headerLeft = new HorizontalLayout(new DrawerToggle(), viewTitle);
        headerLeft.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        var headerRight = new HorizontalLayout(createUserNameField(), createLogoutButton());
        headerRight.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRight.addClassName("header-right");

        var header = new HorizontalLayout(headerLeft, headerRight);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(true, header);
    }

    private Component createUserNameField() {
        return new H5(user.getUsername());
    }

    private Button createLogoutButton() {
        return new Button(new Icon(VaadinIcon.EXIT_O), e -> securityService.logout());
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
