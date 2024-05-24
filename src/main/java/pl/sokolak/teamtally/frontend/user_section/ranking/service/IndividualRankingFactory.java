package pl.sokolak.teamtally.frontend.user_section.ranking.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualDetailsRenderer;
import pl.sokolak.teamtally.frontend.user_section.ranking.renderer.IndividualRankingRenderer;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class IndividualRankingFactory {

    public Component create(Set<ParticipantWithPlace> participantsWithPlace, ParticipantDto participant) {
        Grid<ParticipantWithPlace> gridRanking = createGrid(participantsWithPlace);

        Div body = new Div();
        if (participant.isActive()) {
            participantsWithPlace.stream()
                    .filter(p -> Objects.equals(p.id(), participant.getId()))
                    .findFirst()
                    .ifPresent(participantWithPlace ->
                            body.add(createTitle("Me"),
                                    createGrid(Set.of(participantWithPlace)),
                                    createTitle("Ranking"))
                    );
        }
        body.add(gridRanking);
        return body;
    }

    private Component createTitle(String text) {
        H5 title = new H5(text);
        title.setMaxWidth("600px");
        title.getStyle()
                .set("text-align", "center")
                .set("margin", "5px");
        return title;
    }

    private static Grid<ParticipantWithPlace> createGrid(Set<ParticipantWithPlace> participantsWithPlace) {
        Grid<ParticipantWithPlace> gridRanking = new Grid<>(ParticipantWithPlace.class, false);
        gridRanking.addClassNames("ranking-individual-grid");
        gridRanking.setAllRowsVisible(true);

        gridRanking.addColumn(IndividualRankingRenderer.createPlaces())
                .setTextAlign(ColumnTextAlign.CENTER).setWidth("40px").setFlexGrow(0).setClassNameGenerator(item -> "no-margin");
        gridRanking.addColumn(IndividualRankingRenderer.createParticipants());
        gridRanking.addColumn(IndividualRankingRenderer.createPoints())
                .setTextAlign(ColumnTextAlign.END).setAutoWidth(true).setFlexGrow(0);

        gridRanking.setItems(participantsWithPlace);
        gridRanking.setItemDetailsRenderer(IndividualDetailsRenderer.create());

        sort(gridRanking);
        gridRanking.addClassNames("ranking-grid");
        return gridRanking;
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
