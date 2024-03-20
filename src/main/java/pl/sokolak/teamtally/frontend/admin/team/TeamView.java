package pl.sokolak.teamtally.frontend.admin.team;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "teams", layout = MainView.class)
@PageTitle("Teams")
public class TeamView extends AbstractViewWithSideForm<TeamDto> {

    public TeamView(TeamService service, SessionService sessionService) {
        this.sessionService = sessionService;
        this.service = service;
        this.form = new TeamForm();
        addClassName("team-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(TeamDto.class);
        grid.addClassNames("team-grid");
        grid.setColumns();
        grid.addColumn(TeamRenderer.create()).setHeader("Teams");
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }

    @Override
    protected TeamDto emptyData() {
        return TeamDto.builder().build();
    }

    @Override
    protected List<TeamDto> fetchData() {
        return ((TeamService) service).findAllByEvent(sessionService.getEvent());
    }

    @Override
    protected void saveData(SaveEvent event) {
        TeamDto team = (TeamDto) event.getData();
        team.setEvent(sessionService.getEvent());
        service.save(team);
    }
}
