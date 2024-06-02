package pl.sokolak.teamtally.frontend.admin_section.history;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.sokolak.teamtally.backend.history.HistoryDto;
import pl.sokolak.teamtally.backend.history.HistoryService;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.dialog.AbstractView;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "history", layout = MainView.class)
@PageTitle("History")
public class HistoryView extends AbstractView {

    private Grid<HistoryDto> grid;
    private final HistoryService historyService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSSS").withZone(ZoneId.systemDefault());

    public HistoryView(HistoryService historyService) {
        this.historyService = historyService;
        addClassName("history-view");
        createGrid();
        add(grid);
    }

    private void createGrid() {
        grid = new Grid<>(HistoryDto.class, false);
        grid.addClassNames("history-grid");
//        grid.addColumn("timestamp").setAutoWidth(true);
        grid.addColumn(getTimestampValueProvider()).setHeader("timestamp").setAutoWidth(true).setSortable(true);
        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("operation").setAutoWidth(true);
        grid.setAllRowsVisible(true);
        updateGridItems();
    }

    private void updateGridItems() {
        grid.setItems(historyService.findAll());
    }

    private ValueProvider<HistoryDto, String> getTimestampValueProvider() {
        return code -> Optional.ofNullable(code)
                .map(HistoryDto::getTimestamp)
                .map(t -> formatter.format(t))
                .orElse("");
    }

}
