package pl.sokolak.teamtally.frontend.user_section.profile;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.suggestion.SuggestionService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.util.LogService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/my-profile", layout = MainView.class)
@PageTitle("My profile")
public class ProfileView extends VerticalLayout {

    public ProfileView(SessionService sessionService, LogService log) {

        UserDto user = sessionService.getUser();

        TextField username = new TextField("Username", user.getUsername(), "");
        TextField firstName = new TextField("First name", user.getFirstName(), "");
        TextField lastName = new TextField("Last name", user.getLastName(), "");
        FormLayout form = new FormLayout();
        form.add(username);
        form.add(firstName);
        form.add(lastName);


        form.setEnabled(false);
        form.setMaxWidth("300px");

        add(form);
    }

    private static void handleSubmitAction(SuggestionService suggestionService, SessionService sessionService, LogService log, TextArea textArea) {
        if(textArea.isEmpty()) {
            NotificationService.showWarning("Please write your opinion");
        } else {
            suggestionService.save(
                    SuggestionDto.builder()
                            .user(sessionService.getParticipant().getUser())
                            .event(sessionService.getParticipant().getEvent())
                            .text(textArea.getValue())
                            .build()
            );
            log.info("Suggestion submitted: '{}'", textArea.getValue());
            textArea.clear();
            NotificationService.showSuccess("Thank you!");
        }
    }
}
