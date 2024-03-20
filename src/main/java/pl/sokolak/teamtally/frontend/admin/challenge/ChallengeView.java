package pl.sokolak.teamtally.frontend.admin.challenge;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithSideForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.List;

@SpringComponent(value = "challenge-view-admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "/admin/challenge", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends AbstractViewWithSideForm<ChallengeDto> {

    public ChallengeView(ChallengeService service, SessionService sessionService) {
        this.sessionService = sessionService;
        this.service = service;
        this.form = new ChallengeForm();
        addClassNames("challenge-view");
        init();
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class);
        grid.addClassNames("challenge-grid");
        grid.setColumns();
        grid.addColumn(ChallengeRenderer.create()).setHeader("Challenges");
        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
    }

    @Override
    protected ChallengeDto emptyData() {
        return ChallengeDto.builder().build();
    }

    @Override
    protected List<ChallengeDto> fetchData() {
        return ((ChallengeService) service).findAllByEvent(sessionService.getEvent());
    }

    @Override
    protected void saveData(SaveEvent event) {
        ChallengeDto challenge = (ChallengeDto) event.getData();
        challenge.setEvent(sessionService.getEvent());
        service.save(challenge);
    }
}
