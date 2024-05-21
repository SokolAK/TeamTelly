package pl.sokolak.teamtally.frontend.user_section.ranking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.service.*;

import java.util.List;
import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "ranking")
@PageTitle("Ranking")
public class RankingView extends Div implements BeforeEnterObserver {

    private final SessionService sessionService;
//    private final Span myPointsField = new Span();

    public RankingView(RankingService rankingService,
                       IndividualRankingFactory individualRankingFactory,
                       TeamRankingFactory teamRankingFactory,
                       SessionService sessionService) {

        this.sessionService = sessionService;

        EventDto event = sessionService.getEvent();
        rankingService.init(event);
        Set<ParticipantWithPlace> participantsWithPlaces = rankingService.getParticipantsWithPlaces();
        Component individualRanking = individualRankingFactory.create(participantsWithPlaces, sessionService.getParticipant());

        Set<TeamWithPlace> teamsWithPlaces = rankingService.getTeamsWithPlaces();
        Component teamRanking = teamRankingFactory.create(teamsWithPlaces, participantsWithPlaces);

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("\uD83D\uDC64 Individual", individualRanking);
        tabSheet.add("\uD83D\uDC65 Team", teamRanking);

//        myPointsField.setText(createPoints());
//        eventBus.addListener("my-points", points -> myPointsField.setText(printPoints((Integer) points)));

        add(tabSheet);
    }

    private String createPoints() {
        return printPoints(new PointsCalculator().calculate(sessionService.getParticipant()));
    }

    private String printPoints(Integer points) {
        return "My points: ⭐ " + points;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (sessionService.getEvents().isEmpty()) {
            beforeEnterEvent.rerouteTo("/no-events");
        }
    }
}
