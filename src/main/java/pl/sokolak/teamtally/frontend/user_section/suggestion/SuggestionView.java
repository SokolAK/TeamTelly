package pl.sokolak.teamtally.frontend.user_section.suggestion;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.suggestion.SuggestionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.NotificationService;

import java.awt.*;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/suggest-improvements", layout = MainView.class)
@PageTitle("Suggest improvements")
public class SuggestionView extends VerticalLayout {

    public SuggestionView(SuggestionService suggestionService, SessionService sessionService) {

        TextArea textArea = new TextArea();
        Button sumbitButton = new Button("Submit");
        sumbitButton.addClickListener(click -> {
                    suggestionService.save(
                            SuggestionDto.builder()
                                    .user(sessionService.getParticipant().getUser())
                                    .text(textArea.getValue())
                                    .build()
                    );
                    textArea.clear();
                    NotificationService.showSuccess("Thank you!");
                }
        );

        textArea.setMinWidth("300px");
        textArea.setMaxWidth("600px");
        textArea.setMinHeight("100px");
        textArea.setMaxHeight("100px");

        add(new H4("Please suggest an improvement"), textArea, sumbitButton);
    }
}
