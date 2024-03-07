package pl.sokolak.teamtally.frontend.scoreboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.activity.ActivityDto;
import pl.sokolak.teamtally.backend.activity.ActivityService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.admin.activity.ActivityForm;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "scoreboard")
@PageTitle("Scoreboard")
public class ScoreboardView extends VerticalLayout {


    public ScoreboardView(ActivityService service) {
        addClassName("scoreboard-view");
        add(new H1("HELLO!"));
    }
}