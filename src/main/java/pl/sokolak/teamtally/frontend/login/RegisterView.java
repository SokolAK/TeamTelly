package pl.sokolak.teamtally.frontend.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.common.NotificationService;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final UserService userService;

    public RegisterView(UserService userService) {
        this.userService = userService;
        TextField username = new TextField("Username");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField passwordConfirmation = new PasswordField("Confirm password");
        add(LogoGenerator.createLogo(),
                new H2("Register"),
                username,
                email,
                password,
                passwordConfirmation,
                new Button("Send", event -> register(
                        email.getValue(),
                        password.getValue(),
                        username.getValue(),
                        passwordConfirmation.getValue()
                ))
        );
    }

    private void register(String email, String password, String username, String passwordConfirmation) {
        if (username.trim().isEmpty()) {
            NotificationService.showWarning("Enter an username");
        } else if (password.isEmpty()) {
            NotificationService.showWarning("Enter a password");
//        } else if (password.length() < 6 || password.length() > 12) {
//            NotificationService.showWarning("Password should be of 6-12 characters long");
        } else if (!password.equals(passwordConfirmation)) {
            NotificationService.showError("Passwords do not match");
        } else {
            userService.save(UserDto.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .build());
            UI.getCurrent().navigate("/login");
            NotificationService.showSuccess("HELLO!");
        }
    }
}