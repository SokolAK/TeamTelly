package pl.sokolak.teamtally.frontend.admin.user;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

import java.util.List;

public class UserForm extends AbstractForm {
    private final TextField username = new TextField("Username");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField email = new TextField("Email");
    private final TextField password = new TextField("Password");
    private final ComboBox<UserRoleDto> role = new ComboBox<>("Role");

    public UserForm(List<UserRoleDto> roles) {
        addClassName("challenge-form");
        configureBinder();
        role.setItems(roles);
        role.setItemLabelGenerator(UserRoleDto::getName);
        addFields(username, firstName, lastName, email, role, password);
    }

    private void configureBinder() {
        Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
        binder.bindInstanceFields(this);
        binder.bind(role, UserDto::getUserRole, UserDto::setUserRole);
        setBinder(binder);
    }
}
