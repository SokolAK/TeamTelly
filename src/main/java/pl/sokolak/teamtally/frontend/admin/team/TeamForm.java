package pl.sokolak.teamtally.frontend.admin.team;

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
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;

public class TeamForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField personalPoints = new TextField("Personal points");
    TextField teamPoints = new TextField("Team points");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ChallengeDto> binder = new BeanValidationBinder<>(ChallengeDto.class);

    public TeamForm() {
        addClassName("team-form");
        binder.bindInstanceFields(this);
        add(name, personalPoints, teamPoints, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

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


    public void setChallenge(ChallengeDto challenge) {
        binder.setBean(challenge);
    }

    // Events
    public static abstract class ChallengeFormEvent extends ComponentEvent<TeamForm> {
        private final ChallengeDto challenge;

        protected ChallengeFormEvent(TeamForm source, ChallengeDto challenge) {
            super(source, false);
            this.challenge = challenge;
        }

        public ChallengeDto getChallenge() {
            return challenge;
        }
    }

    public static class SaveEvent extends ChallengeFormEvent {
        SaveEvent(TeamForm source, ChallengeDto challenge) {
            super(source, challenge);
        }
    }

    public static class DeleteEvent extends ChallengeFormEvent {
        DeleteEvent(TeamForm source, ChallengeDto challenge) {
            super(source, challenge);
        }

    }

    public static class CloseEvent extends ChallengeFormEvent {
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