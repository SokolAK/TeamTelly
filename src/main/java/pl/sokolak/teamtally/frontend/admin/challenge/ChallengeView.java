package pl.sokolak.teamtally.frontend.admin.challenge;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope("prototype")
@RolesAllowed("ADMIN")
@Route(value = "challenges", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends VerticalLayout {

    private final Grid<ChallengeDto> grid = new Grid<>(ChallengeDto.class);
    private final TextField filterText = new TextField();
    private final ChallengeService service;
    private ChallengeForm form;

    public ChallengeView(ChallengeService service) {
        this.service = service;
        addClassName("challenge-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(5, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ChallengeForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveChallenge);
        form.addDeleteListener(this::deleteChallenge);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveChallenge(ChallengeForm.SaveEvent event) {
        service.save(event.getChallenge());
        updateList();
        closeEditor();
    }

    private void deleteChallenge(ChallengeForm.DeleteEvent event) {
        service.delete(event.getChallenge());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("challenge-grid");
        grid.setColumns();
        grid.addColumn("name").setHeader("Name");
        grid.addColumn("personalPoints").setHeader("Personal points");
        grid.addColumn("teamPoints").setHeader("Team points");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editChallenge(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addChallengeButton = new Button("Add challenge");
        addChallengeButton.addClickListener(click -> addChallenge());

        var toolbar = new HorizontalLayout(filterText, addChallengeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editChallenge(ChallengeDto challenge) {
        if (challenge == null) {
            closeEditor();
        } else {
            form.setChallenge(challenge);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setChallenge(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addChallenge() {
        grid.asSingleSelect().clear();
        editChallenge(ChallengeDto.builder().build());
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}