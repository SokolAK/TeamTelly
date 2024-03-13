package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractView;
import pl.sokolak.teamtally.frontend.common.ReloadService;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/event", layout = MainView.class)
@PageTitle("Event")
public class EventView extends AbstractView<EventDto> {

    private final SecurityService securityService;

    public EventView(EventService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
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
        form.addSaveListener(this::saveOrUpdateData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(EventDto.class);
        grid.addClassNames("event-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn("name");
        grid.addColumn("startDate");
        grid.addColumn("endDate");
        grid.addColumn(u -> u.getOwner().getEmail()).setHeader(t("view.event.owner"));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }

    @Override
    protected EventDto emptyData() {
        return EventDto.builder().build();
    }

    @Override
    protected void saveData(SaveEvent event) {
        EventDto eventDto = (EventDto) event.getData();
        eventDto.setOwner(securityService.getAuthenticatedUser());
        service.save(eventDto);
    }

    @Override
    protected boolean shouldReloadAppLayoutOnSaveOrUpdate() {
        return true;
    }
}
