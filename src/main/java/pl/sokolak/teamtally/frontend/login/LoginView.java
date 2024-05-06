package pl.sokolak.teamtally.frontend.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final UserService userService;
    private final LoginForm loginForm = new LoginForm();

    private final TextField username = new TextField("Username");
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField passwordConfirmation = new PasswordField("Confirm password");


    public LoginView(UserService userService) {
        this.userService = userService;
        prepareStyles();
        prepareLoginForm();
        add(LogoGenerator.createLogo(), loginForm);

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Sign in");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

//        Button button = new Button("Sign in", e -> dialog.open());
//        add(dialog, button);

        add(dialog);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

    private void prepareStyles() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void prepareLoginForm() {
        loginForm.setAction("login");
        loginForm.setI18n(createLoginFormStrings());
        loginForm.setForgotPasswordButtonVisible(false);
    }

    private LoginI18n createLoginFormStrings() {
        LoginI18n loginFormStrings = LoginI18n.createDefault();
        loginFormStrings.getForm().setUsername(t("login.form.email"));
        loginFormStrings.getErrorMessage().setUsername(t("login.form.email.required"));
        loginFormStrings.getErrorMessage().setTitle(t("login.form.error.title"));
        loginFormStrings.getErrorMessage().setMessage(t("login.form.error.message"));
        return loginFormStrings;
    }

    private VerticalLayout createDialogLayout() {
        VerticalLayout dialogLayout = new VerticalLayout(
                username,
                firstName,
                lastName,
                email,
                password,
                passwordConfirmation
        );
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Sign in", event -> {
            if (register(
                    username.getValue(),
                    firstName.getValue(),
                    lastName.getValue(),
                    email.getValue(),
                    password.getValue(),
                    passwordConfirmation.getValue())) {
                dialog.close();
            }
        }
        );
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    private boolean register(String username, String firstName, String lastName, String email,
                             String password, String passwordConfirmation) {
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
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .build());
            UI.getCurrent().navigate("/login");
            NotificationService.showSuccess("HELLO!");
            return true;
        }
        return false;
    }
}
