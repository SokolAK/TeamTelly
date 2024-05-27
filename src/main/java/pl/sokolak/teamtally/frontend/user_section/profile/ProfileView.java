package pl.sokolak.teamtally.frontend.user_section.profile;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.backend.util.ImageUtil;
import pl.sokolak.teamtally.backend.util.LogService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.util.Objects;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/my-profile", layout = MainView.class)
@PageTitle("My profile")
public class ProfileView extends VerticalLayout {

    private final static String width = "400px";
    private final UserService userService;
    private final SessionService sessionService;
    private PasswordField newPasswordInput;
    private PasswordField confirmPasswordInput;
    private Button changePasswordButton;
    private Span newPasswordHelper;
    private Span confirmPasswordHelper;

    public ProfileView(SessionService sessionService, UserService userService, LogService log) {

        this.userService = userService;

        UserDto user = sessionService.getUser();

        HorizontalLayout photo = createValueRow(ImageUtil.createUserPhotoAsImageLarge(user.getPhoto()));
        HorizontalLayout username = createValueRow(new H4(user.getUsername()));

        HorizontalLayout firstName = createLabelValueRow("First name", new Span(user.getFirstName()));
        HorizontalLayout lastName = createLabelValueRow("Last name", new Span(user.getLastName()));
        HorizontalLayout email = createLabelValueRow("Email", new Span(user.getEmail()));
        HorizontalLayout jobTitle = createLabelValueRow("Job title", new Span(user.getJobTitle()));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(photo, username,
                firstName, lastName, jobTitle, email, new Hr(),
                createChangePasswordForm());

        verticalLayout.setMaxWidth(width);
        verticalLayout.setWidthFull();

        add(verticalLayout);
        this.sessionService = sessionService;
    }

    private VerticalLayout createChangePasswordForm() {
        newPasswordInput = createNewPasswordField();
        confirmPasswordInput = createConfirmPasswordField();
        changePasswordButton = createChangePasswordButton();

        HorizontalLayout newPassword = createLabelValueRow("New password", newPasswordInput);
        HorizontalLayout confirmPassword = createLabelValueRow("Confirm password", confirmPasswordInput);
        HorizontalLayout changePassword = createValueRow(changePasswordButton);

        VerticalLayout verticalLayout = new VerticalLayout(newPassword, confirmPassword, changePassword);
        verticalLayout.setMaxWidth(width);
        verticalLayout.setWidthFull();
        verticalLayout.setPadding(false);

        return verticalLayout;
    }

    private Button createChangePasswordButton() {
        Button changePasswordButton = new Button("Change password");
        changePasswordButton.addClickListener(e -> {
            updateConfirmPasswordHelper();
            String newPasswordValue = newPasswordInput.getValue();
            String confirmPasswordValue = confirmPasswordInput.getValue();
            if (newPasswordValue.equals(confirmPasswordValue)) {
                if(newPasswordValue.length() < 6) {
                    newPasswordHelper.getStyle().set("color", "var(--lumo-error-color)");
                } else {
                    userService.updatePassword(sessionService.getUser(), newPasswordValue);
                    NotificationService.showSuccess("Password has been changed");
                }
            }
        });

        return changePasswordButton;
    }

    private PasswordField createNewPasswordField() {
        PasswordField newPasswordInput = new PasswordField();
        newPasswordInput.setMinLength(6);
        newPasswordInput.setMaxLength(12);
        newPasswordHelper = new Span("6 to 12 characters");
        newPasswordInput.setHelperComponent(newPasswordHelper);

        newPasswordInput.setValueChangeMode(ValueChangeMode.EAGER);
        newPasswordInput.addValueChangeListener(e -> newPasswordHelper.getStyle().remove("color"));

        return newPasswordInput;
    }

    private PasswordField createConfirmPasswordField() {
        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setMinLength(6);
        confirmPasswordInput.setMaxLength(12);
        confirmPasswordHelper = new Span();
        confirmPasswordInput.setHelperComponent(confirmPasswordHelper);

        confirmPasswordInput.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPasswordInput.addValueChangeListener(e -> {
            updateConfirmPasswordHelper();
        });

        return confirmPasswordInput;
    }

    private HorizontalLayout createValueRow(Component component) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setAlignItems(Alignment.CENTER);
        layout.setMaxWidth(width);
        layout.setWidthFull();
        layout.add(component);
        return layout;
    }

    private HorizontalLayout createLabelValueRow(String label, Component value) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setAlignItems(Alignment.CENTER);
        layout.setMaxWidth(width);
        layout.setWidthFull();
        Span labelComponent = new Span(label + ":");
        Span valueComponent = new Span(value);
        labelComponent.getStyle().set("font-style", "italic");
        layout.add(labelComponent, valueComponent);
        return layout;
    }

    private void updateConfirmPasswordHelper() {
        String newPasswordValue = newPasswordInput.getValue();
        String confirmPasswordValue = confirmPasswordInput.getValue();
        if (Objects.equals(newPasswordValue, confirmPasswordValue)) {
            confirmPasswordHelper.setText("");
        } else {
            confirmPasswordHelper.setText("Passwords do not match");
            confirmPasswordHelper.getStyle().set("color", "var(--lumo-error-color)");
        }
    }
}
