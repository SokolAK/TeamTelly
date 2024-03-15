package pl.sokolak.teamtally.frontend.admin.challenge;

import com.vaadin.flow.component.grid.Grid;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.admin.event.SaveEvent;
import pl.sokolak.teamtally.frontend.common.AbstractViewWithForm;

import java.util.List;

//@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@PermitAll
//@Route(value = "/challenge", layout = MainView.class)
//@PageTitle("Challenges")
public class ChallengeViewCopy extends AbstractViewWithForm<ChallengeDto> {

    private SessionService sessionService;

    public ChallengeViewCopy(ChallengeService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
        addClassName("challenge-view");
        configureForm();
        configureGrid();
        configureView();
        updateList();
    }

    @Override
    protected void configureForm() {
        form = new ChallengeForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveOrUpdateData);
        form.addDeleteListener(this::deleteData);
        form.addCloseListener(e -> closeEditor());
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class);
        grid.addClassNames("challenge-grid");
        grid.setSizeFull();
        grid.setColumns("name", "personalPoints", "teamPoints");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
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
