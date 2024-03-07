package pl.sokolak.teamtally.frontend.admin.activity;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.textfield.TextField;
import pl.sokolak.teamtally.backend.activity.ActivityDto;

public class ActivityForm extends ThreeButtonsForm<ActivityDto> {
    private final TextField name = new TextField("Name");
    private final TextField personalPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");

    public ActivityForm() {
        addClassName("activity-form");
        configure(ActivityDto.class);
        setSaveButtonListener(event -> validateAndSave());
        setDeleteButtonListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        setCloseButtonListener(event -> fireEvent(new CloseEvent(this)));
        addFields(name, personalPoints, teamPoints);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setActivity(ActivityDto activity) {
        binder.setBean(activity);
    }

    public static abstract class ActivityFormEvent extends ComponentEvent<ActivityForm> {
        private final ActivityDto activity;

        protected ActivityFormEvent(ActivityForm source, ActivityDto activity) {
            super(source, false);
            this.activity = activity;
        }

        public ActivityDto getActivity() {
            return activity;
        }
    }

    public static class SaveEvent extends ActivityFormEvent {
        SaveEvent(ActivityForm source, ActivityDto activity) {
            super(source, activity);
        }
    }

    public static class DeleteEvent extends ActivityFormEvent {
        DeleteEvent(ActivityForm source, ActivityDto activity) {
            super(source, activity);
        }
    }

    public static class CloseEvent extends ActivityFormEvent {
        CloseEvent(ActivityForm source) {
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
