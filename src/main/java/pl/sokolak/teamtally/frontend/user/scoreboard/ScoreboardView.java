package pl.sokolak.teamtally.frontend.user.scoreboard;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "scoreboard")
@PageTitle("Scoreboard")
public class ScoreboardView extends VerticalLayout {


    public ScoreboardView() {
        addClassName("scoreboard-view");
        add(new H1("Scores"));
    }
}