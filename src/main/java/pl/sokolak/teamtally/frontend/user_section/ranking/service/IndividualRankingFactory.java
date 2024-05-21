package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IndividualRankingFactory {

    public Component create(Set<ParticipantWithPlace> participantsWithPlace, ParticipantDto participant) {
        Grid<ParticipantWithPlace> grid = new Grid<>(ParticipantWithPlace.class);
        grid.addClassNames("ranking-individual-grid");
        grid.setAllRowsVisible(true);

        grid.setColumns();
        grid.addColumn(IndividualRankingRenderer.createPlaces())
                .setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true).setFlexGrow(0).setClassNameGenerator(item -> "no-margin");
        grid.addColumn(IndividualRankingRenderer.createParticipants());
        grid.addColumn(IndividualRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        grid.setItems(participantsWithPlace);
        grid.setItemDetailsRenderer(IndividualDetailsRenderer.create());

        sort(grid);

        H5 h5 = new H5(createPoints(participant));
//        h5.addClassName("right-align-div");
        return new Div(h5, grid);
    }

    private String createPoints(ParticipantDto participant) {
        return printPoints(new PointsCalculator().calculate(participant));
    }

    private String printPoints(Integer points) {
        return "My points: ⭐ " + points;
    }

    private static void sort(Grid<ParticipantWithPlace> grid) {
        grid.addColumn(ParticipantWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }
}
