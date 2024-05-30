package pl.sokolak.teamtally.frontend.common;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.frontend.common.event.CloseEvent;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

public abstract class SaveCancelAbstractForm extends AbstractSideForm {

    private final Button save = new Button("Save");
    private final Button close = new Button("Cancel");

    protected Binder binder;

    public SaveCancelAbstractForm() {
    }

    protected void setBinder(Binder<?> binder) {
        this.binder = binder;
    }

    protected void addFields(Component... components) {
        add(components);
        add(createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }

    public void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setData(Data data) {
        binder.setBean(data);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }
}
