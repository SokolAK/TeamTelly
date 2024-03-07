package pl.sokolak.teamtally.frontend.admin.activity;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public abstract class ThreeButtonsForm<T> extends FormLayout {
    protected final Button save = new Button("Save");
    protected final Button delete = new Button("Delete");
    protected final Button close = new Button("Cancel");

    protected Binder<T> binder;

    public ThreeButtonsForm() {
    }

    protected void addFields(Component... components) {
        add(components);
        add(createButtonsLayout());
    }

    protected Component createButtonsLayout() {
        return new HorizontalLayout(save, delete, close);
    }

    protected void configure(Class<T> clazz) {
        configureBinder(clazz);
        configureButtons();
    }

    private void configureBinder(Class<T> clazz) {
        binder = new BeanValidationBinder<>(clazz);
        binder.bindInstanceFields(this);
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
    }

    private void configureButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
    }

    protected void setSaveButtonListener(ComponentEventListener<ClickEvent<Button>> listener) {
        save.addClickListener(listener);
    }

    protected void setDeleteButtonListener(ComponentEventListener<ClickEvent<Button>> listener) {
        delete.addClickListener(listener);
    }

    protected void setCloseButtonListener(ComponentEventListener<ClickEvent<Button>> listener) {
        close.addClickListener(listener);
    }
}
