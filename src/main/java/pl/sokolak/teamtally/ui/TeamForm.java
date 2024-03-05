package pl.sokolak.teamtally.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.activity.ActivityDto;

public class TeamForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField personalPoints = new TextField("Personal points");
    TextField teamPoints = new TextField("Team points");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<ActivityDto> binder = new BeanValidationBinder<>(ActivityDto.class);

    public TeamForm() {
        addClassName("team-form");
        binder.bindInstanceFields(this);
        add(name, personalPoints, teamPoints, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }


    public void setActivity(ActivityDto activity) {
        binder.setBean(activity);
    }

    // Events
    public static abstract class ActivityFormEvent extends ComponentEvent<TeamForm> {
        private final ActivityDto activity;

        protected ActivityFormEvent(TeamForm source, ActivityDto activity) {
            super(source, false);
            this.activity = activity;
        }

        public ActivityDto getActivity() {
            return activity;
        }
    }

    public static class SaveEvent extends ActivityFormEvent {
        SaveEvent(TeamForm source, ActivityDto activity) {
            super(source, activity);
        }
    }

    public static class DeleteEvent extends ActivityFormEvent {
        DeleteEvent(TeamForm source, ActivityDto activity) {
            super(source, activity);
        }

    }

    public static class CloseEvent extends ActivityFormEvent {
        CloseEvent(TeamForm source) {
            super(source, null);
        }
    }

    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        addListener(DeleteEvent.class, listener);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }


}