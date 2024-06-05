package pl.sokolak.teamtally.frontend.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Acknowledgment {
    public static Component create() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMaxWidth("600px");
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        Component title = new H4("Great job Ziflow! \uD83D\uDC99");
        title.getStyle().set("margin-bottom", "10px");
        Span message1 = new Span("Thank you for your participation in the Ziflow Meetup 2024 Challenge! " +
                "Please leave any feedback you have in the suggestion tab so that next time the challenge can be even better.");
        message1.getStyle().set("text-align", "center");

        Span message2 = new Span("Travel safe!");
        message2.getStyle().set("margin-top", "10px");
        Span message3 = new Span("Iga & Adam");

        layout.add(title);
        layout.add(message1);
        layout.add(message2);
        layout.add(message3);

        Div div = new Div(layout);
        div.getStyle().set("border", "2px solid #cbe6ef");
        div.getStyle().set("background", "#e9f5f8");
        div.setMaxWidth("600px");

        return div;
    }
}
