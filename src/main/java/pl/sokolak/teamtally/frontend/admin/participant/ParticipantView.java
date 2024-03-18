package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithForm;
import pl.sokolak.teamtally.frontend.admin.challenge.ChallengeRenderer;

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
        addClassName("participants-view");
        init();
    }

    @Override
    protected void configureForm() {
        form = new ParticipantForm();
        form.addSaveListener(this::saveOrUpdateData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ParticipantDto.class);
        grid.addClassNames("participant-grid");
        grid.setColumns();
//        grid.setColumns("name", "personalPoints", "teamPoints");
//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addColumn(ParticipantRenderer.create()).setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, participant) -> {
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
                })).setAutoWidth(true);
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
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
