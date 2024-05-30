//package pl.sokolak.teamtally.frontend.common.dialog;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import lombok.NoArgsConstructor;
//import pl.sokolak.teamtally.abstracts.Data;
//import pl.sokolak.teamtally.abstracts.Service;
//import pl.sokolak.teamtally.backend.session.SessionService;
//import pl.sokolak.teamtally.backend.util.LogService;
//import pl.sokolak.teamtally.frontend.common.event.DeleteEvent;
//import pl.sokolak.teamtally.frontend.common.event.SaveEvent;
//import pl.sokolak.teamtally.frontend.service.ReloadService;
//
//import java.util.Comparator;
//import java.util.List;
//
//@NoArgsConstructor
//public abstract class AbstractViewWithDialog<T extends Data> extends VerticalLayout {
//
//    protected Grid<T> grid;
//    protected Service<T> service;
//    protected SessionService sessionService;
//    private final LogService log = new LogService();
//
//    protected void init() {
//        configureForm();
//        configureFormCommon();
//        configureGrid();
//        configureGridCommon();
//        configureView();
//        updateList();
//    }
//
//    protected void configureForm() {};
//
//    protected abstract void configureGrid();
//
//    protected void configureFormCommon() {
//        form.addSaveListener(e -> {
//            saveOrUpdateData(e);
//            closeEditorAndUpdateList();
//        });
//        form.addDeleteListener(this::deleteData);
//        form.addCloseListener(e -> closeEditor());
//    }
//
//    protected void configureGridCommon() {
//        grid.setAllRowsVisible(true);
//        grid.setWidthFull();
//    }
//
//    protected void configureView() {
//        setSizeFull();
//        add(getToolbar(), getContent());
//        closeEditor();
//    }
//
//    private HorizontalLayout getContent() {
//        HorizontalLayout content = new HorizontalLayout(grid);
////        content.setFlexGrow(1, grid);
//        content.addClassNames("content");
//        content.setSizeFull();
//        return content;
//    }
//
//    protected Component getToolbar() {
//        Button addDataButton = new Button("Add");
//        addDataButton.addClickListener(click -> {
//            log.info("'Add' button clicked [{}]", this.getClass().getSimpleName());
//            addData();
//        });
//
//        var toolbar = new HorizontalLayout(addDataButton);
//        toolbar.addClassName("toolbar");
//        return toolbar;
//    }
//
//    protected void editData(Data data) {
//        log.info("Open edit form [{}]", this.getClass().getSimpleName());
//        if (data == null) {
//            closeEditor();
//        } else {
//            dialog.open();
//            form.setData(convertToFormData(data));
////            form.setVisible(true);
////            addClassName("editing");
//        }
//    }
//
//    protected Data convertToFormData(Data data) {
//        return data;
//    }
//
//    protected void closeEditor() {
//        form.setData(null);
////        form.setVisible(false);
//        removeClassName("editing");
//    }
//
//    protected void addData() {
//        grid.asSingleSelect().clear();
//        editData(emptyData());
//    }
//
//    protected abstract T emptyData();
//
//    protected void saveOrUpdateData(SaveEvent event) {
//        if (shouldUpdate(event)) {
//            updateData(event);
//        } else {
//            saveData(event);
//        }
//        closeDialog();
//    }
//
//    protected void closeEditorAndUpdateList() {
//        updateList();
//        closeEditor();
//        if(shouldReloadAppLayoutOnSaveOrUpdate()) {
//            ReloadService.reloadAppLayout();
//        }
//    }
//
//    protected boolean shouldReloadAppLayoutOnSaveOrUpdate() {
//        return false;
//    }
//
//    protected boolean shouldUpdate(SaveEvent event) {
//        return false;
//    }
//
//    protected void saveData(SaveEvent event) {
//        service.save((T) event.getData());
//    }
//
//    protected void updateData(SaveEvent event) {
//        saveData(event);
//    }
//
//    protected Comparator<T> getComparator() {
//        return Comparator.comparingInt(Data::getId);
//    }
//
//    protected void deleteData(DeleteEvent event) {
//        service.delete((T) event.getData());
//        updateList();
//        closeEditor();
//    }
//
//    protected void updateList() {
//        List<T> items = fetchData();
//        if(!items.isEmpty()) {
//            items.sort(getComparator());
//        }
//        grid.setItems(items);
//        grid.setVisible(!items.isEmpty());
//    }
//
//    protected List<T> fetchData() {
//        return service.findAll();
//    }
//}
