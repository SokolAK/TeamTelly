package pl.sokolak.teamtally.frontend.common.dialog;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Setter;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.util.CustomValidator;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.function.Consumer;


@Setter
public class DialogForm<T extends Data> extends Dialog {

    private final AbstractForm<T> form;
    private Consumer<T> saveCallback;
    private Consumer<T> deleteCallback;
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;

    public DialogForm(AbstractForm<T> form) {
        this.form = form;

        add(new VerticalLayout(form));

        saveButton = new Button("Save", event -> {
            if (form.validate()) {
                saveCallback.accept(form.getData());
                NotificationService.showSuccess("Saved");
                close();
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        deleteButton = new Button("Delete", event -> {
            deleteCallback.accept(form.getData());
            NotificationService.showSuccess("Deleted");
            close();
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        cancelButton = new Button("Cancel", event -> {
            close();
        });
    }

    public void openDialog(T data) {
        form.setData(data);
        configButtons(data.getId() == null);
        open();
    }

    public void addValidator(CustomValidator<T> validator) {
        form.addValidator(validator);
    }

    private void configButtons(boolean isNewItem) {
        getFooter().removeAll();
        if (isNewItem) {
            getFooter().add(saveButton);
            getFooter().add(cancelButton);
        } else {
            getFooter().add(saveButton);
            getFooter().add(deleteButton);
            getFooter().add(cancelButton);
        }
    }
}
