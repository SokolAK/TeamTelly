package pl.sokolak.teamtally.frontend.user_section.suggestion;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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
import pl.sokolak.teamtally.backend.util.LogService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/suggest-improvements", layout = MainView.class)
@PageTitle("Suggest improvements")
public class SuggestionView extends VerticalLayout {

    public SuggestionView(SuggestionService suggestionService, SessionService sessionService, LogService log) {

        TextArea textArea = new TextArea();
        Button sumbitButton = new Button("Send", new Icon(VaadinIcon.ARROW_CIRCLE_RIGHT));
        Div buttonDiv = new Div(sumbitButton);
        buttonDiv.setMaxWidth("600px");
        buttonDiv.setWidthFull();
        sumbitButton.getStyle().set("margin", "auto");
        sumbitButton.getStyle().set("display", "block");
        sumbitButton.addClickListener(click -> {
                    handleSubmitAction(suggestionService, sessionService, log, textArea);
                }
        );

        textArea.addClassName("suggestion-textarea");
        textArea.setWidthFull();
        textArea.setMaxLength(4000);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        textArea.addValueChangeListener(e ->
                e.getSource().setHelperText(e.getValue().length() + "/" + 4000));

        Component message = new Span(t("view.suggestions.message"));
        message.getStyle().set("maxWidth", "600px");
        message.getStyle().set("text-wrap", "balance");

        add(new H5(t("view.suggestions.title")), message, textArea, buttonDiv);
    }

    private static void handleSubmitAction(SuggestionService suggestionService, SessionService sessionService, LogService log, TextArea textArea) {
        if(textArea.isEmpty()) {
            NotificationService.showWarning("Please write your opinion");
        } else {
            suggestionService.save(
                    SuggestionDto.builder()
                            .user(sessionService.getParticipant().getUser())
                            .text(textArea.getValue())
                            .build()
            );
            log.info("Suggestion submitted: '{}'", textArea.getValue());
            textArea.clear();
            NotificationService.showSuccess("Thank you!");
        }
    }
}
