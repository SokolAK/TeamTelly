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

public abstract class AbstractView<T extends Data> extends VerticalLayout {

    protected HorizontalLayout toolbar;
    protected Grid<T> grid;
    protected Service<T> service;

    public AbstractView() {
    }

    protected void init() {
        configureToolbar();
        configureGrid();
        configureView();
        updateList();
    }

    protected abstract void configureToolbar();

    protected abstract void configureGrid();

    protected void configureView() {
        setSizeFull();
        add(getToolbar(), getContent());
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout();
        if (toolbar != null) {
            content.add(toolbar);
        }
        content.add(grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    protected Component getToolbar() {

        var toolbar = new HorizontalLayout();
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    protected void updateList() {
        grid.setItems(fetchData());
    }

    protected List<T> fetchData() {
        return service.findAll();
    }
}
