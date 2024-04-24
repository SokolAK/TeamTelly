package pl.sokolak.teamtally.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.mainlayout.DrawerFactory;
import pl.sokolak.teamtally.frontend.mainlayout.HeaderFactory;


public class MainView extends AppLayout {

    private final String appVersion = "0.3.0";
    private final SecurityService securityService;
    private final SessionService sessionService;
    private final H4 viewTitle = new H4();

    public MainView(SecurityService securityService, SessionService sessionService) {
        this.securityService = securityService;
        this.sessionService = sessionService;
        init();
    }

    private void init() {
        sessionService.init();
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    public void reload() {
        Component content = getContent();
        remove(getChildren().toArray(Component[]::new));
        init();
        setContent(content);
    }

    public void reload(EventDto event) {
        sessionService.reinit(event);
        UI.getCurrent().getPage().reload();
    }

    private void addDrawerContent() {
        DrawerFactory drawerFactory = new DrawerFactory(sessionService);
        Component logo = drawerFactory.createLogo();
        Component scroller = drawerFactory.createScroller();
        Component eventName = drawerFactory.createEventBanner();
        Component eventDate = drawerFactory.createEventDate();
        Component events = drawerFactory.createEvents(selection -> reload(selection.getValue()));
        Footer footer = drawerFactory.createFooter(appVersion);

//        addToDrawer(logo);
        if (sessionService.hasEvent()) {
            addToDrawer(eventName, eventDate);
        }
        if (sessionService.getEvents().size() > 1) {
            addToDrawer(events);
        }
        addToDrawer(scroller, footer);
    }

    private void addHeaderContent() {
        HeaderFactory headerFactory = new HeaderFactory(sessionService, securityService);
        addToNavbar(true, headerFactory.create(viewTitle));
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
