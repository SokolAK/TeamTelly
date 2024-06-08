package pl.sokolak.teamtally.frontend.user_section.ranking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.calculator.PointsCalculator;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.util.LogService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.service.*;

import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "ranking")
@PageTitle("Ranking")
@Log4j2
public class RankingView extends Div implements BeforeEnterObserver {

    private final SessionService sessionService;
//    private final Span myPointsField = new Span();

    public RankingView(RankingService rankingService,
                       IndividualRankingFactory individualRankingFactory,
                       TeamRankingFactory teamRankingFactory,
                       SessionService sessionService,
                       LogService log) {

        this.sessionService = sessionService;
        log.info("Show ranking view");

        EventDto event = sessionService.getEvent();
        ParticipantDto participant = sessionService.getParticipant();
        rankingService.init(event);
        Set<ParticipantWithPlace> participantsWithPlaces = rankingService.getParticipantsWithPlaces();
        Component individualRanking = individualRankingFactory.create(participantsWithPlaces, participant);

        Set<TeamWithPlace> teamsWithPlaces = rankingService.getTeamsWithPlaces();
        Component teamRanking = teamRankingFactory.create(teamsWithPlaces, participantsWithPlaces, participant);

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
