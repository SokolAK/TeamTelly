package pl.sokolak.teamtally.frontend.admin_section.suggestion;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.event.EventService;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDataView;
import pl.sokolak.teamtally.backend.suggestion.SuggestionDto;
import pl.sokolak.teamtally.backend.suggestion.SuggestionService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.MainView;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringComponent(value = "suggestion-view-admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/suggestions", layout = MainView.class)
@PageTitle("Suggestions")
public class SuggestionView extends VerticalLayout {

    private final SuggestionService suggestionService;
    private final UserService userService;
    private final EventService eventService;
    private final Grid<SuggestionDto> grid;
    private List<SuggestionDto> suggestions;
    private boolean shownAll = false;

    public SuggestionView(SuggestionService suggestionService, UserService userService, EventService eventService) {
        this.suggestionService = suggestionService;
        this.userService = userService;
        this.eventService = eventService;
        grid = createGrid();
        addClassName("suggestion-view");
        add(createButtonShowAll(), grid);
    }

    private Button createButtonShowAll() {
        Button button = new Button("Show all");
        button.addClickListener(b -> {
            shownAll = !shownAll;
            button.setText(shownAll ? "Hide all" : "Show all");
            suggestions.forEach(p -> grid.setDetailsVisible(p, shownAll));
        });
        return button;
    }

    private Grid<SuggestionDto> createGrid() {
        Set<SuggestionDataView> suggestionsData = suggestionService.findAllData();
        Set<Integer> userIds = suggestionsData.stream()
                .map(SuggestionDataView::getUserId)
                .collect(Collectors.toSet());
        Set<Integer> EventIds = suggestionsData.stream()
                .map(SuggestionDataView::getEventId)
                .collect(Collectors.toSet());

        List<UserDto> users = userService.findAllDataByIds(userIds);
        List<EventDto> events = eventService.findAllDataByIds(EventIds);

        suggestions = suggestionsData.stream()
                .map(s -> SuggestionDto.builder()
                        .id(s.getId())
                        .text(s.getText())
                        .user(buildUser(s.getUserId(), users))
                        .event(buildEvent(s.getEventId(), events))
                        .build())
                .sorted(Comparator.comparing(SuggestionDto::getId))
                .collect(Collectors.toList());


        Grid<SuggestionDto> grid = new Grid<>(SuggestionDto.class, false);
        grid.addClassNames("suggestion-admin-grid");
        grid.addColumn(SuggestionRenderer.create());
        grid.setItemDetailsRenderer(SuggestionDetailsRenderer.create());
//        suggestions.forEach(p -> grid.setDetailsVisible(p, true));
        grid.setAllRowsVisible(true);
        grid.setItems(suggestions);

        return grid;
    }

    private UserDto buildUser(Integer id, List<UserDto> users) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .map(u -> UserDto.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .email(u.getEmail())
                        .photo(u.getPhoto())
                        .build())
                .orElse(null);
    }

    private EventDto buildEvent(Integer id, List<EventDto> users) {
        return users.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> EventDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build())
                .orElse(null);
    }
}
