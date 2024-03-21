package pl.sokolak.teamtally.frontend.user_section.challenge;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.AbstractView;
import pl.sokolak.teamtally.frontend.admin_section.challenge.ChallengeRenderer;

import java.util.List;
import java.util.UUID;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PermitAll
@Route(value = "/challenge", layout = MainView.class)
@PageTitle("Challenges")
public class ChallengeView extends AbstractView<ChallengeDto> {

    private final SessionService sessionService;

    public ChallengeView(ChallengeService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
        addClassName("challenge-view");
        init();
    }

    @Override
    protected void configureToolbar() {
        toolbar = new HorizontalLayout();
        TextField codeField = new TextField();
        codeField.setPlaceholder("Insert code");
        Button confirmButton = new Button(new Icon(VaadinIcon.STAR));
        toolbar.setFlexGrow(1, codeField);
        toolbar.setWidthFull();
        toolbar.add(codeField, confirmButton);
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(ChallengeDto.class, false);
        grid.addClassNames("challenge-grid");
        grid.setSizeFull();
        grid.setColumns();
//        grid.addColumn(ChallengeRenderer.create())
        grid.addColumn(ChallengeRenderer.create(
                        List.of(UUID.fromString("afb75070-8176-4ccc-81ed-b0ea786335e2"),
                                UUID.fromString("3b7b5558-7319-4a7d-8882-8e5825bba707")),
                        List.of(UUID.fromString("3b7b5558-7319-4a7d-8882-8e5825bba707"))))
                .setHeader("Name")
                .setComparator(ChallengeDto::getName);
        grid.sort(List.of(new GridSortOrder<>(grid.getColumns().get(0), SortDirection.ASCENDING)));
    }

    @Override
    protected List<ChallengeDto> fetchData() {
        return ((ChallengeService) service).findAllByEvent(sessionService.getEvent());
    }
}
