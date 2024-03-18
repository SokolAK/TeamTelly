package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.List;

@SpringComponent(value = "participant-view-admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/participant", layout = MainView.class)
@PageTitle("Participants")
public class ParticipantView extends AbstractViewWithForm<ParticipantDto> {

    public ParticipantView(ParticipantService service, SessionService sessionService) {
        this.sessionService = sessionService;
        this.service = service;
        this.form = new ParticipantForm();
        addClassName("participants-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ParticipantDto.class);
        grid.addClassNames("participant-grid");
        grid.setColumns();
        grid.addColumn(ParticipantRenderer.create()).setAutoWidth(true);
        grid.addColumn(createDeleteButtonRenderer()).setAutoWidth(true).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }

    private ComponentRenderer<Button, ParticipantDto> createDeleteButtonRenderer() {
        return new ComponentRenderer<>(Button::new, (button, participant) -> {
            button.addClassName("delete-button");
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(s -> {
                service.delete(participant);
                updateList();
                closeEditor();
            });
            button.setIcon(new Icon(VaadinIcon.TRASH));
        });
    }

    @Override
    protected ParticipantDto emptyData() {
        return ParticipantDto.builder().build();
    }

    @Override
    protected List<ParticipantDto> fetchData() {
        return ((ParticipantService) service).findAllByEvent(sessionService.getEvent());
    }

    @Override
    protected void saveData(SaveEvent event) {
        ParticipantDto participant = (ParticipantDto) event.getData();
        participant.setEvent(sessionService.getEvent());
        service.save(participant);
    }
}
