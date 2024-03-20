package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;
import pl.sokolak.teamtally.frontend.service.ReloadService;
import pl.sokolak.teamtally.frontend.common.event.DeleteEvent;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/event", layout = MainView.class)
@PageTitle("Events")
public class EventView extends AbstractViewWithSideForm<EventDto> {

    private final SecurityService securityService;

    public EventView(EventService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
        this.form = new EventForm();
        addClassName("event-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(EventDto.class);
        grid.addClassNames("event-grid");
        grid.setColumns();
        grid.addColumn(EventRenderer.create());
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

    @Override
    protected void deleteData(DeleteEvent event) {
        service.delete((EventDto) event.getData());
        updateList();
        closeEditor();
        ReloadService.reloadAppLayout();
    }
}
