package pl.sokolak.teamtally.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.sokolak.teamtally.activity.Activity;
import pl.sokolak.teamtally.activity.ActivityDto;
import pl.sokolak.teamtally.activity.ActivityService;

import java.util.List;

@PageTitle("Activities")
@Route(value = "/activities", layout = MainView.class)
public class ActivityView extends HorizontalLayout {

    private final ActivityService activityService;

//    private final Grid<Activity> grid = new Grid<>(Activity.class);

    public ActivityView(ActivityService activityService) {
        this.activityService = activityService;
        VerticalLayout cardContainer = new VerticalLayout();
        cardContainer.setWidthFull();

        // Add some sample card items
        cardContainer.add(createCard("Activities", "This is the content of card 1"));

        // Add the card container to the main layout
        add(cardContainer);
    }

    private Div createCard(String title, String content) {
        Div card = new Div();
        card.addClassName("card");
        card.setWidth("500px"); // You can adjust the width as needed

        // Card content
        Div cardTitle = new Div();
        cardTitle.addClassName("card-title");
        cardTitle.setText(title);

        Div cardContent = new Div();
        cardContent.addClassName("card-content");
        cardContent.setText(content);

        Grid<ActivityDto> grid = new Grid<>(ActivityDto.class, false);
        grid.addColumn(ActivityDto::getName).setHeader("name");
        grid.addColumn(ActivityDto::getPersonalPoints).setHeader("personal points");
        grid.addColumn(ActivityDto::getTeamPoints).setHeader("team points");
        List<ActivityDto> activities = activityService.findAll();
        grid.setItems(activities);
//        add(grid);

        card.add(cardTitle, grid);
        return card;
    }
}