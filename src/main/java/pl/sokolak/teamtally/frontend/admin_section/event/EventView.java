package pl.sokolak.teamtally.frontend.admin_section.event;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import lombok.Builder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.security.SecurityService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;
import pl.sokolak.teamtally.frontend.service.ReloadService;
import pl.sokolak.teamtally.frontend.common.event.DeleteEvent;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.HashSet;
import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/event", layout = MainView.class)
@PageTitle("Events")
public class EventView extends AbstractViewWithSideForm<EventDto> {

    private final SecurityService securityService;
    private final ParticipantService participantService;

    public EventView(EventService service, ParticipantService participantService, SecurityService securityService) {
        this.service = service;
        this.participantService = participantService;
        this.securityService = securityService;
        this.form = new EventForm();
        addClassName("event-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(EventDto.class, false);
        grid.addClassNames("event-grid");
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
        UserDto owner = securityService.getAuthenticatedUser();
        eventDto.setOwner(owner);
        EventDto savedEvent = service.save(eventDto);
        saveParticipant(savedEvent, owner);
    }

    private void saveParticipant(EventDto eventDto, UserDto owner) {
        ParticipantDto ownerParticipant = ParticipantDto.builder()
                .active(true)
                .event(eventDto)
                .user(owner)
                .build();
        participantService.save(ownerParticipant);
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
