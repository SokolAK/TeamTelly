package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.team.TeamDto;
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

    public Component create(Set<TeamWithPlace> teamsWithPlaces,
                            Set<ParticipantWithPlace> participantsWithPlace,
                            ParticipantDto participant) {
        Grid<TeamWithPlace> grid = new Grid<>(TeamWithPlace.class);
        grid.addClassNames("ranking-team-grid");
        grid.setAllRowsVisible(true);

        grid.setColumns();
        grid.addColumn(TeamRankingRenderer.createPlaces())
                .setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(TeamRankingRenderer.createTeams());
        grid.addColumn(TeamRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        grid.setItems(teamsWithPlaces);
        grid.setItemDetailsRenderer(TeamDetailsRenderer.create(participantsWithPlace));

        sort(grid);
        HorizontalLayout legend = new HorizontalLayout(
                new Icon(VaadinIcon.INFO_CIRCLE_O),
                new Span("Team points = (sum of individual points) / (number of members) * 10 + team bonuses"));
        legend.getElement().getThemeList().add("badge");

        H5 myTeam = new H5(createMyTeam(participant));
        return new Div(myTeam, grid, legend);
    }

    private Component createMyTeam(ParticipantDto participant) {
        TeamDto team = participant.getTeam();
        if(team == null) {
            return new Span();
        }
        Span span = new Span(team.getIcon() + " " + team.getName());
        span.getStyle().set("color", team.getColor());
        return new Div(new Span("My team: "), span);
    }

    private static void sort(Grid<TeamWithPlace> grid) {
        grid.addColumn(TeamWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }
}
