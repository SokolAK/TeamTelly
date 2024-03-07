package pl.sokolak.teamtally.frontend.login;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm loginForm = new LoginForm();

	public LoginView(){
		prepareStyles();
		prepareLoginForm();
		add(createLogo(), loginForm);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if(beforeEnterEvent.getLocation()
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
	}

	private LoginI18n createLoginFormStrings() {
		LoginI18n loginFormStrings = LoginI18n.createDefault();
		loginFormStrings.getForm().setUsername("Email");
		loginFormStrings.getErrorMessage().setUsername("Email is required");
		loginFormStrings.getErrorMessage().setTitle("Incorrect email or password");
		loginFormStrings.getErrorMessage().setMessage("Please check that you have entered the correct email and password and try again.");
		return loginFormStrings;
	}

	private static Image createLogo() {
		var logo = new Image("assets/logo.png", "Team Tally");
		logo.addClassName("logo-medium");
		return logo;
	}
}
