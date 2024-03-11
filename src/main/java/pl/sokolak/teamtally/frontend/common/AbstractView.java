package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.sokolak.teamtally.backend.Data;
import pl.sokolak.teamtally.backend.Service;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.common.event.DeleteEvent;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.function.Function;

public abstract class AbstractView<T extends Data> extends VerticalLayout {

    protected Grid<T> grid;
    protected Service<T> service;
    protected AbstractForm form;
    private Function<Data, Data> formDataExtractor = Function.identity();

    public AbstractView() {
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

    private Component getToolbar() {
        Button addDataButton = new Button("Add");
        addDataButton.addClickListener(click -> addData());

        var toolbar = new HorizontalLayout(addDataButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    protected void setFormDataExtractor(Function<Data,Data> extractor) {
        this.formDataExtractor = extractor;
    }

    protected void editData(Data data) {
        if (data == null) {
            closeEditor();
        } else {
            form.setData(formDataExtractor.apply(data));
            form.setVisible(true);
            addClassName("editing");
        }
    }

    protected void closeEditor() {
        form.setData(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addData() {
        grid.asSingleSelect().clear();
        editData(ChallengeDto.builder().build());
    }

    protected void saveData(SaveEvent event) {
        service.save((T) event.getData());
        updateList();
        closeEditor();
    }

    protected void deleteData(DeleteEvent event) {
        service.delete((T) event.getData());
        updateList();
        closeEditor();
    }

    protected void updateList() {
        grid.setItems(service.findAll());
    }
}
