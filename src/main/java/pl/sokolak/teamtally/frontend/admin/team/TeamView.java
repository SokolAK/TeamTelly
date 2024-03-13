package pl.sokolak.teamtally.frontend.admin.team;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.frontend.MainView;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "teams", layout = MainView.class)
@PageTitle("Teams")
public class TeamView extends VerticalLayout {

    private final Grid<TeamDto> grid = new Grid<>(TeamDto.class);
    private final TeamService service;
    private TeamForm form;

    public TeamView(TeamService service) {
        this.service = service;
        addClassName("team-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(5, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new TeamForm();
        form.setWidth("25em");
//        form.addSaveListener(this::saveContact);
//        form.addDeleteListener(this::deleteContact);
//        form.addCloseListener(e -> closeEditor());
    }
//
//    private void saveContact(TeamForm.SaveEvent event) {
//        service.save(event.getChallenge());
//        updateList();
//        closeEditor();
//    }
//
//    private void deleteContact(ChallengeForm.DeleteEvent event) {
//        service.delete(event.getChallenge());
//        updateList();
//        closeEditor();
//    }

    private void configureGrid() {
        grid.addClassNames("team-grid");
//        grid.setMaxWidth("300px");
//        grid.setMinWidth("0");
        grid.setColumns();
        grid.addColumn(createTeamRenderer()).setHeader("Team");
//        grid.addColumn("icon").setHeader("").setWidth("5em").setFlexGrow(0);
//        grid.addColumn("name").setHeader("");

//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
//        grid.asSingleSelect().addValueChangeListener(event ->
//                editChallenge(event.getValue()));
    }

    private Component getToolbar() {
        Button addChallengeButton = new Button("Add team");
        addChallengeButton.addClickListener(click -> addChallenge());

        var toolbar = new HorizontalLayout(addChallengeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editChallenge(ChallengeDto challenge) {
        if (challenge == null) {
            closeEditor();
        } else {
            form.setChallenge(challenge);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setChallenge(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addChallenge() {
        grid.asSingleSelect().clear();
        editChallenge(ChallengeDto.builder().build());
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

    private Renderer<TeamDto> createTeamRenderer() {
        return LitRenderer.<TeamDto> of(
                        "<div style=\"color:#${item.color}\">${item.icon} <b>${item.name}</b></div>\n")
                .withProperty("color", TeamDto::color)
                .withProperty("icon", TeamDto::icon)
                .withProperty("name", TeamDto::name);
    }
}