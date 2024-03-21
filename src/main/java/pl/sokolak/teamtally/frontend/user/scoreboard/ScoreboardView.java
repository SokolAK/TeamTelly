package pl.sokolak.teamtally.frontend.user.scoreboard;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.exception.NoEventsView;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "ranking")
@PageTitle("Ranking")
public class ScoreboardView extends Div {

    public ScoreboardView() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Individual",
                new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Team",
                new Div(new Text("This is the Payment tab content")));
        add(tabSheet);
    }
}
