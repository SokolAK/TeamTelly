package pl.sokolak.teamtally.frontend.other;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "no-events", layout = MainView.class)
@PageTitle("")
public class NoEventsView extends VerticalLayout {

    public NoEventsView() {
        addClassName("no-events-view");
        add(new H1("It seems that you are not participating in any event :c"));
    }
}
