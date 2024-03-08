package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.role.UserRole;
import pl.sokolak.teamtally.frontend.admin.challenge.ThreeButtonsForm;

import java.util.List;

public class UserForm extends ThreeButtonsForm<UserDto> {
    private final TextField username = new TextField("Username");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField email = new TextField("Email");
//    private final TextField role = new TextField("Role");
    private final TextField password = new TextField("Password");
    private final ComboBox<UserRole> role = new ComboBox<>("Role");

    public UserForm(List<UserRole> roles) {
        addClassName("user-form");
        configure(UserDto.class);
        setSaveButtonListener(event -> validateAndSave());
        setDeleteButtonListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        setCloseButtonListener(event -> fireEvent(new CloseEvent(this)));
        role.setItems(roles);
        role.setItemLabelGenerator(UserRole::getName);
        binder.bind(role, UserDto::getUserRole, UserDto::setUserRole);
        addFields(username, firstName, lastName, email, role, password);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setUser(UserDto user) {
        binder.setBean(user);
    }

    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private final UserDto user;

        protected UserFormEvent(UserForm source, UserDto user) {
            super(source, false);
            this.user = user;
        }

        public UserDto getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        SaveEvent(UserForm source, UserDto user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        DeleteEvent(UserForm source, UserDto user) {
            super(source, user);
        }
    }

    public static class CloseEvent extends UserFormEvent {
        CloseEvent(UserForm source) {
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
