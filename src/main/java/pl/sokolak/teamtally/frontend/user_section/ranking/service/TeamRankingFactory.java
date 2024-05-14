package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.team.TeamService;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.TeamDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.TeamRankingRenderer;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TeamRankingFactory {

    public Component create(Set<TeamWithPlace> teamsWithPlaces, Set<ParticipantWithPlace> participantsWithPlace) {
        Grid<TeamWithPlace> grid = new Grid<>(TeamWithPlace.class);
        grid.addClassNames("ranking-team-grid");
        grid.setAllRowsVisible(true);

        grid.setColumns();
        grid.addColumn(TeamRankingRenderer.createPlaces())
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(TeamRankingRenderer.createTeams());
        grid.addColumn(TeamRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        grid.setItems(teamsWithPlaces);
        grid.setItemDetailsRenderer(TeamDetailsRenderer.create(participantsWithPlace));

        sort(grid);

        return new Div(grid);
    }

    private static void sort(Grid<TeamWithPlace> grid) {
        grid.addColumn(TeamWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }
}
