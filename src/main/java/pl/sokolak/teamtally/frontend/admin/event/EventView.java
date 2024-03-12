package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractView;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "/event", layout = MainView.class)
@PageTitle("Event")
public class EventView extends AbstractView<ChallengeDto> {

    public EventView(ChallengeService service) {
        this.service = service;
        addClassName("event-view");
        configureForm();
        configureGrid();
        configureView();
        updateList();
    }

    @Override
    protected void configureForm() {
        form = new EventForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class);
        grid.addClassNames("event-grid");
        grid.setSizeFull();
        grid.setColumns("name", "date", "owner_id");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }
}
