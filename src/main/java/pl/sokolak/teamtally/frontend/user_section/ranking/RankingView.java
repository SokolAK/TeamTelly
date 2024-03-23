package pl.sokolak.teamtally.frontend.user_section.ranking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.PlaceRenderer;

import java.util.List;
import java.util.Optional;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "ranking")
@PageTitle("Ranking")
public class RankingView extends Div {

    private final ParticipantService participantService;
    private final SessionService sessionService;
    private final PointsCalculator pointsCalculator;

    public RankingView(ParticipantService participantService,
                       SessionService sessionService,
                       PointsCalculator pointsCalculator) {
        this.participantService = participantService;
        this.sessionService = sessionService;
        this.pointsCalculator = pointsCalculator;
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Individual", createIndividualRanking());
        tabSheet.add("Team", createTeamRanking());
        add(tabSheet);
    }

    private Component createIndividualRanking() {
        List<ParticipantDto> participants = participantService.findAllActiveByEvent(sessionService.getEvent());
        List<ParticipantWithPoints> participantWithPoints = participants.stream()
                .map(p -> new ParticipantWithPoints(
                        p.getUser().getUsername(),
                        p.getUser().getFirstName(),
                        p.getUser().getLastName(),
                        Optional.ofNullable(p.getTeam()).map(TeamDto::getIcon).orElse(null),
                        Optional.ofNullable(p.getTeam()).map(TeamDto::getName).orElse(null),
                        Optional.ofNullable(p.getTeam()).map(TeamDto::getColor).orElse(null),
                        pointsCalculator.calculate(p)
                )).toList();

        Grid<ParticipantWithPoints> grid = new Grid<>(ParticipantWithPoints.class);
        grid.addClassNames("ranking-individual-grid");
        grid.setAllRowsVisible(true);
        grid.setColumns();
        grid.addColumn(PlaceRenderer.create()).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(IndividualRankingRenderer.createParticipants()).setAutoWidth(true);
        grid.addColumn(IndividualRankingRenderer.createPoints()).setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(ParticipantWithPoints::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(3), SortDirection.DESCENDING)));
        grid.setItems(participantWithPoints);

        return new Div(grid);
    }

    private Component createTeamRanking() {
        return new Div(new Text("Team"));
    }
}
