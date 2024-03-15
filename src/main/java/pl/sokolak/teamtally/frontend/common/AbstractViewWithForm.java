package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.frontend.admin.event.DeleteEvent;
import pl.sokolak.teamtally.frontend.admin.event.SaveEvent;

import java.util.List;

public abstract class AbstractViewWithForm<T extends Data> extends VerticalLayout {

    protected Grid<T> grid;
    protected Service<T> service;
    protected AbstractForm form;

    public AbstractViewWithForm() {
    }

    protected abstract void configureForm();

    protected abstract void configureGrid();

    protected void configureView() {
        setSizeFull();
        add(getToolbar(), getContent());
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

    protected Component getToolbar() {
        Button addDataButton = new Button("Add");
        addDataButton.addClickListener(click -> addData());

        var toolbar = new HorizontalLayout(addDataButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    protected void editData(Data data) {
        if (data == null) {
            closeEditor();
        } else {
            form.setData(convertToFormData(data));
            form.setVisible(true);
            addClassName("editing");
        }
    }

    protected Data convertToFormData(Data data) {
        return data;
    }

    protected void closeEditor() {
        form.setData(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    protected void addData() {
        grid.asSingleSelect().clear();
        editData(emptyData());
    }

    protected abstract T emptyData();

    protected void saveOrUpdateData(SaveEvent event) {
        if (shouldUpdate(event)) {
            updateData(event);
        } else {
            saveData(event);
        }
        updateList();
        closeEditor();

        if(shouldReloadAppLayoutOnSaveOrUpdate()) {
            ReloadService.reloadAppLayout();
        }
    }

    protected boolean shouldReloadAppLayoutOnSaveOrUpdate() {
        return false;
    }

    protected boolean shouldUpdate(SaveEvent event) {
        return false;
    }

    protected void saveData(SaveEvent event) {
        service.save((T) event.getData());
    }

    protected void updateData(SaveEvent event) {
        saveData(event);
    }

    protected void deleteData(DeleteEvent event) {
        service.delete((T) event.getData());
        updateList();
        closeEditor();
    }

    protected void updateList() {
        grid.setItems(fetchData());
    }

    protected List<T> fetchData() {
        return service.findAll();
    }
}
