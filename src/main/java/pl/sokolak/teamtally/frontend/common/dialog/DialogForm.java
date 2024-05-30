package pl.sokolak.teamtally.frontend.common.dialog;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Builder;
import lombok.Setter;

import java.util.function.Consumer;


@Setter
public class DialogForm<T> extends Dialog {

    private final AbstractForm<T> form;
    private Consumer<T> saveCallback;
    private Consumer<T> deleteCallback;

    public DialogForm(AbstractForm<T> form) {
        this.form = form;

        add(new VerticalLayout(form));

        Button saveButton = new Button("Save", event -> {
            saveCallback.accept(form.getData());
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button deleteButton = new Button("Delete", event -> {
            deleteCallback.accept(form.getData());
            close();
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button cancelButton = new Button("Cancel", event -> {
            close();
        });

        getFooter().add(saveButton);
        getFooter().add(deleteButton);
        getFooter().add(cancelButton);
    }

    public void openDialog(T data) {
        form.setData(data);
        open();
    }


//
//
//    protected void configureBinder() {}
//
//    void save() {
//        binder.validate();
//        if (binder.isValid()) {
//            fireEvent(new SaveEvent(this, binder.getBean()));
//        }
//    }
//
//    void delete() {
//        fireEvent(new DeleteEvent(this, binder.getBean()));
//    }
//
//    public void setData(Data data) {
//        binder.setBean(data);
//    }
//
//
//    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
//        addListener(DeleteEvent.class, listener);
//    }
//
//    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
//        addListener(SaveEvent.class, listener);
//    }
//
//    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
//        addListener(CloseEvent.class, listener);
//    }
}
