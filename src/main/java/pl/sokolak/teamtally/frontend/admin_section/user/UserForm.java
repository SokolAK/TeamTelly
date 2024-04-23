package pl.sokolak.teamtally.frontend.admin_section.user;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.role.UserRoleDto;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

import java.util.List;

class UserForm extends SaveDeleteCancelAbstractForm {
    private final TextField username = new TextField("Username");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField jobTitle = new TextField("Job Title");
    private final EmailField email = new EmailField("Email");
    private final TextField password = new TextField("Password");
    private final ComboBox<UserRoleDto> role = new ComboBox<>("Role");

    public UserForm(List<UserRoleDto> roles) {
        addClassName("user-form");
        configureBinder();
        role.setItems(roles);
        role.setItemLabelGenerator(UserRoleDto::getName);
        addFields(username, firstName, lastName, jobTitle, email, role, password);
    }

    private void configureBinder() {
        Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
        binder.bindInstanceFields(this);
        binder.bind(role, UserDto::getUserRole, UserDto::setUserRole);
        setBinder(binder);
    }
}
