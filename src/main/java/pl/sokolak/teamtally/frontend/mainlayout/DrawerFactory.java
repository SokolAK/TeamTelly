package pl.sokolak.teamtally.frontend.mainlayout;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import lombok.AllArgsConstructor;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.admin_section.challenge.ChallengeView;
import pl.sokolak.teamtally.frontend.admin_section.event.EventView;
import pl.sokolak.teamtally.frontend.admin_section.participant.ParticipantView;
import pl.sokolak.teamtally.frontend.admin_section.team.TeamView;
import pl.sokolak.teamtally.frontend.admin_section.user.UserView;
import pl.sokolak.teamtally.frontend.user_section.ranking.RankingView;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@AllArgsConstructor
public class DrawerFactory {

    private final SessionService sessionService;

    public Image createLogo() {
        Image logo = new Image("assets/logo.png", "TeamTally");
        logo.addClassName("logo-small");
        return logo;
    }

    public Component createEventBanner() {
        H1 eventBanner = new H1(sessionService.getEventName());
        eventBanner.addClassName("event-name");
        return eventBanner;
    }

    public Component createEventDate() {
        String eventStartDate = Optional.ofNullable(sessionService.getEventStartDate()).map(d -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).orElse("");
        String eventEndDate = Optional.ofNullable(sessionService.getEventEndDate()).map(d -> d.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).orElse("");
        H2 eventDate;
        if (eventStartDate.isEmpty() && eventEndDate.isEmpty()) {
            eventDate = new H2();
        } else {
            eventDate = new H2(eventStartDate + " - " + eventEndDate);
        }
        eventDate.addClassName("event-date");
        return eventDate;
    }

    public Component createEvents(
            ValueChangeListener<ComponentValueChangeEvent<ComboBox<EventDto>, EventDto>> changeValueListener) {
        ComboBox<EventDto> events = new ComboBox<>();
        events.setItems(sessionService.getEvents());
        events.setPlaceholder("change event");
        events.setItemLabelGenerator(EventDto::getName);
        events.addValueChangeListener(changeValueListener);
        events.addClassName("event-combo");
        events.addClassName("disable-selection");
        events.addClassName("disable-caret");
        return events;
    }

    public Footer createFooter(String appVersion) {
        Footer layout = new Footer();
        H3 version = new H3("TeamTally v. " + appVersion);
        version.addClassName("version");
        layout.add(version);
        return layout;
    }

    public Scroller createScroller() {
        Scroller scroller = new Scroller(createNavigation());
        scroller.addClassName("scroller");
        return scroller;
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        createNavigationUser(nav);
        if (sessionService.getUser().isAdmin()) {
            createNavigationEventAdmin(nav);
            createNavigationApplicationAdmin(nav);
        }
        return nav;
    }

    private void createNavigationUser(SideNav nav) {
        if (sessionService.hasEvent()) {
            nav.addItem(new SideNavItem("Ranking", RankingView.class, VaadinIcon.TROPHY.create()));
            nav.addItem(new SideNavItem("Challenges", pl.sokolak.teamtally.frontend.user_section.challenge.ChallengeView.class, VaadinIcon.ROCKET.create()));
            nav.addItem(new SideNavItem("Suggest improvements", pl.sokolak.teamtally.frontend.user_section.suggestion.SuggestionView.class, VaadinIcon.LIGHTBULB.create()));
        }
    }

    private void createNavigationEventAdmin(SideNav nav) {
        SideNavItem label = new SideNavItem("Event administration");
        label.addClassName("side-nav-label");
        nav.addItem(label);
        nav.addItem(new SideNavItem("Events", EventView.class, VaadinIcon.STAR.create()));
        if (sessionService.hasEvent()) {
            nav.addItem(new SideNavItem("Teams", TeamView.class, VaadinIcon.USERS.create()));
            nav.addItem(new SideNavItem("Participants", ParticipantView.class, VaadinIcon.USER.create()));
            nav.addItem(new SideNavItem("Challenges", ChallengeView.class, VaadinIcon.ROCKET.create()));
        }
    }

    private void createNavigationApplicationAdmin(SideNav nav) {
        SideNavItem label = new SideNavItem("Application administration");
        label.addClassName("side-nav-label");
        nav.addItem(label);
        nav.addItem(new SideNavItem("Users", UserView.class, VaadinIcon.GROUP.create()));
    }
}
