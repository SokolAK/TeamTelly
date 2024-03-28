package pl.sokolak.teamtally.frontend.user_section.ranking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.ParticipantWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPlace;
import pl.sokolak.teamtally.frontend.user_section.ranking.dto.TeamWithPoints;
import pl.sokolak.teamtally.frontend.user_section.ranking.service.IndividualRankingService;
import pl.sokolak.teamtally.frontend.user_section.ranking.service.TeamRankingService;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "", layout = MainView.class)
@RouteAlias(value = "ranking")
@PageTitle("Ranking")
public class RankingView extends Div implements BeforeEnterObserver {

    private final SessionService sessionService;

    public RankingView(IndividualRankingService individualRankingService,
                       TeamRankingService teamRankingService,
                       SessionService sessionService) {

        this.sessionService = sessionService;

        EventDto event = sessionService.getEvent();
        List<ParticipantWithPlace> participantsWithPlace = individualRankingService.getParticipantsWithPlaces(event);
        List<TeamWithPlace> teamsWithPlace = teamRankingService.getTeamsWithPlaces(event);
        Component individualRanking = individualRankingService.create(participantsWithPlace);
        Component teamRanking = teamRankingService.create(teamsWithPlace, participantsWithPlace);

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Individual", individualRanking);
        tabSheet.add("Team", teamRanking);
        add(tabSheet);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(sessionService.getEvents().isEmpty()) {
            beforeEnterEvent.rerouteTo("/no-events");
        }
    }
}
