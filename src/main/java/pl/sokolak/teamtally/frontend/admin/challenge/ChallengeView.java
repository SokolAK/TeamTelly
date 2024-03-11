package pl.sokolak.teamtally.frontend.admin.challenge;

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
@Route(value = "/challenge", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends AbstractView<ChallengeDto> {

    public ChallengeView(ChallengeService service) {
        this.service = service;
        addClassName("challenge-view");
        configureForm();
        configureGrid();
        configureView();
        updateList();
    }

    @Override
    protected void configureForm() {
        form = new ChallengeForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class);
        grid.addClassNames("challenge-grid");
        grid.setSizeFull();
        grid.setColumns("name", "personalPoints", "teamPoints");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }
}
