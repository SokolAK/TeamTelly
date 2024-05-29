package pl.sokolak.teamtally.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.router.PageTitle;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.util.EventBus;
import pl.sokolak.teamtally.frontend.mainlayout.DrawerFactory;
import pl.sokolak.teamtally.frontend.mainlayout.HeaderFactory;


public class MainView extends AppLayout {

    private static final String APP_VERSION = "0.8.0";
    private final SecurityService securityService;
    private final SessionService sessionService;
    private final H4 viewTitle = new H4();
    private final EventBus eventBus;

    public MainView(SecurityService securityService, SessionService sessionService, EventBus eventBus) {
        this.securityService = securityService;
        this.sessionService = sessionService;
        this.eventBus = eventBus;
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
        Footer footer = drawerFactory.createFooter(APP_VERSION);

        addToDrawer(logo);
//        if (sessionService.hasEvent()) {
//            addToDrawer(eventName, eventDate);
//        }
//        if (sessionService.getEvents().size() > 1) {
//            addToDrawer(events);
//        }
        addToDrawer(scroller, footer);
    }

    private void addHeaderContent() {
        HeaderFactory headerFactory = new HeaderFactory(sessionService, securityService, eventBus);
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
