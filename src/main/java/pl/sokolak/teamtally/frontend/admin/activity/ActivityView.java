package pl.sokolak.teamtally.frontend.admin.activity;

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
import pl.sokolak.teamtally.backend.activity.ActivityDto;
import pl.sokolak.teamtally.backend.activity.ActivityService;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope("prototype")
@RolesAllowed("ADMIN")
@Route(value = "activities", layout = MainView.class)
@PageTitle("Activities")
public class ActivityView extends VerticalLayout {

    private final Grid<ActivityDto> grid = new Grid<>(ActivityDto.class);
    private final TextField filterText = new TextField();
    private final ActivityService service;
    private ActivityForm form;

    public ActivityView(ActivityService service) {
        this.service = service;
        addClassName("activity-view");
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
        form = new ActivityForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveActivity);
        form.addDeleteListener(this::deleteActivity);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveActivity(ActivityForm.SaveEvent event) {
        service.save(event.getActivity());
        updateList();
        closeEditor();
    }

    private void deleteActivity(ActivityForm.DeleteEvent event) {
        service.delete(event.getActivity());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("activity-grid");
        grid.setColumns();
        grid.addColumn("name").setHeader("Name");
        grid.addColumn("personalPoints").setHeader("Personal points");
        grid.addColumn("teamPoints").setHeader("Team points");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editActivity(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addActivityButton = new Button("Add activity");
        addActivityButton.addClickListener(click -> addActivity());

        var toolbar = new HorizontalLayout(filterText, addActivityButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editActivity(ActivityDto activity) {
        if (activity == null) {
            closeEditor();
        } else {
            form.setActivity(activity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setActivity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addActivity() {
        grid.asSingleSelect().clear();
        editActivity(ActivityDto.builder().build());
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}