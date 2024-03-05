package pl.sokolak.teamtally.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.sokolak.teamtally.scoreboard.ScoreboardView;


//@CssImport("./styles/views/main/main-view.css")
//@JsModule("./styles/shared-styles.js")
@Route("")
public class MainView extends AppLayout {

    private H2 viewTitle;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
//        ThemeUtil.setThemeVariant(Lumo.LIGHT);
    }


    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Scroller scroller = new Scroller(createNavigation());
        Image logo = new Image("assets/logo2.png", "");
        logo.addClassName("logo");
        addToDrawer(logo, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Activities", ActivityView.class, VaadinIcon.STAR.create()));
        nav.addItem(new SideNavItem("Scoreboard", ScoreboardView.class, VaadinIcon.TROPHY.create()));

        return nav;
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
