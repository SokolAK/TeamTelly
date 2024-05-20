package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IndividualRankingFactory {

    public Component create(Set<ParticipantWithPlace> participantsWithPlace) {
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

        return new Div(grid);
    }

    private static void sort(Grid<ParticipantWithPlace> grid) {
        grid.addColumn(ParticipantWithPlace::points).setVisible(false);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(grid.getColumns().size() - 1), SortDirection.DESCENDING)));
    }
}
