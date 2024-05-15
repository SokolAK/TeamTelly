package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.abstracts.Service;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractView<T extends Data> extends VerticalLayout {

    protected Grid<T> grid;
    protected Service<T> service;

    public AbstractView() {
    }

    protected void init() {
        configureGrid();
        configureView();
        updateList();
    }

    protected abstract void configureGrid();

    protected void configureView() {
        setSizeFull();
        Component toolbar = getToolbar();
        toolbar.addClassName("toolbar");
        add(toolbar, getContent());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout();
//        if (toolbar != null) {
//            content.add(toolbar);
//        }
        content.add(grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    protected Component getToolbar() {
        return new HorizontalLayout();
    }

    protected void updateList() {
        List<T> items = fetchData();
        items.sort(getComparator());
        grid.setItems(items);
        grid.setVisible(items.size() > 0);
    }

    protected List<T> fetchData() {
        return service.findAll();
    }

    protected Comparator<T> getComparator() {
        return Comparator.comparingInt(Data::getId);
    }
}
