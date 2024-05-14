package pl.sokolak.teamtally.frontend.user_section.ranking;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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

    public RankingView(RankingService rankingService,
                       IndividualRankingFactory individualRankingFactory,
                       TeamRankingFactory teamRankingFactory,
                       SessionService sessionService) {

        this.sessionService = sessionService;

        EventDto event = sessionService.getEvent();
        rankingService.init(event);
        Set<ParticipantWithPlace> participantsWithPlaces = rankingService.getParticipantsWithPlaces();
        Component individualRanking = individualRankingFactory.create(participantsWithPlaces);


        Set<TeamWithPlace> teamsWithPlaces = rankingService.getTeamsWithPlaces();
        Component teamRanking = teamRankingFactory.create(teamsWithPlaces, participantsWithPlaces);

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Individual", individualRanking);
        tabSheet.add("Team", teamRanking);
        add(tabSheet);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (sessionService.getEvents().isEmpty()) {
            beforeEnterEvent.rerouteTo("/no-events");
        }
    }
}
