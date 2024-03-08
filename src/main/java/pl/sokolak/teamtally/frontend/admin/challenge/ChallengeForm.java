package pl.sokolak.teamtally.frontend.admin.challenge;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.textfield.TextField;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;

public class ChallengeForm extends ThreeButtonsForm<ChallengeDto> {
    private final TextField name = new TextField("Name");
    private final TextField personalPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");

    public ChallengeForm() {
        addClassName("challenge-form");
        configure(ChallengeDto.class);
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

    public void setChallenge(ChallengeDto challenge) {
        binder.setBean(challenge);
    }

    public static abstract class ChallengeFormEvent extends ComponentEvent<ChallengeForm> {
        private final ChallengeDto challenge;

        protected ChallengeFormEvent(ChallengeForm source, ChallengeDto challenge) {
            super(source, false);
            this.challenge = challenge;
        }

        public ChallengeDto getChallenge() {
            return challenge;
        }
    }

    public static class SaveEvent extends ChallengeFormEvent {
        SaveEvent(ChallengeForm source, ChallengeDto challenge) {
            super(source, challenge);
        }
    }

    public static class DeleteEvent extends ChallengeFormEvent {
        DeleteEvent(ChallengeForm source, ChallengeDto challenge) {
            super(source, challenge);
        }
    }

    public static class CloseEvent extends ChallengeFormEvent {
        CloseEvent(ChallengeForm source) {
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
