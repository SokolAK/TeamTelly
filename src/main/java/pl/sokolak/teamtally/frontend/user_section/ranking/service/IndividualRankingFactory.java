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
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantChallengeRankingView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantRankingView;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPoints;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IndividualRankingFactory {

    public Component create(Set<ParticipantWithPlace> participantsWithPlace) {
        Grid<ParticipantWithPlace> grid = new Grid<>(ParticipantWithPlace.class);
        grid.addClassNames("ranking-individual-grid");
        grid.setAllRowsVisible(true);

        grid.setColumns();
        grid.addColumn(IndividualRankingRenderer.createPlaces())
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(IndividualRankingRenderer.createParticipants());
        grid.addColumn(IndividualRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        grid.setItems(participantsWithPlace);
        grid.setItemDetailsRenderer(IndividualDetailsRenderer.create());

        sort(grid);

        return new Div(grid);
    }

    private static void sort(Grid<ParticipantWithPlace> grid) {
        grid.addColumn(ParticipantWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }
}
