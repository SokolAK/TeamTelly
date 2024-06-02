package pl.sokolak.teamtally.frontend.admin_section.code;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.firitin.components.DynamicFileDownloader;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.challenge.ChallengeService;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.code.CodeService;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.session.SessionService;
import pl.sokolak.teamtally.backend.util.CustomValidator;
import pl.sokolak.teamtally.frontend.MainView;
import pl.sokolak.teamtally.frontend.common.dialog.AbstractView;
import pl.sokolak.teamtally.frontend.common.dialog.DialogForm;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RolesAllowed("ADMIN")
@Route(value = "codes", layout = MainView.class)
@PageTitle("Codes")
public class CodeView extends AbstractView {

    private DialogForm<CodeDto> codeFormDialog;
    private Grid<CodeDto> codesGrid;
    private CodeService codeService;
    private SessionService sessionService;
    private List<CodeDto> codes;
    private Set<String> initialCodeIds;

    public CodeView(CodeService codeService, ChallengeService challengeService, ParticipantService participantService, SessionService sessionService) {
        this.sessionService = sessionService;
        this.codeService = codeService;
        Set<ChallengeDto> challenges = challengeService.findAllDataByEvent(sessionService.getEvent());
        addClassName("code-view");

        createGrid();
        createCodeFormDialog(challenges, participantService);

        add(new HorizontalLayout(createAddButton(), createCsvDownloader()), codesGrid, codeFormDialog);
    }

    private Button createAddButton() {
        Button addButton = new Button(t("Add"), new Icon(VaadinIcon.PLUS_CIRCLE));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(__ -> codeFormDialog.openDialog(new CodeDto()));
        return addButton;
    }

    private DynamicFileDownloader createCsvDownloader() {
        Button button = new Button("Export to file", new Icon(VaadinIcon.FILE_TABLE));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return new DynamicFileDownloader(button, "codes.csv", out -> {
            PrintWriter writer = new PrintWriter(out);
            writer.println("code,max usages,challenge,individual points,team points,from");
            codes.forEach(c -> {
                writer.println("%s,%s,%s,%s,%s,%s".formatted(
                        getOrEmpty(c.getCode()),
                        getOrEmpty(c.getMaxUsages()),
                        getOrEmpty(c.getChallenge().getName()),
                        getOrEmpty(c.getChallenge().getIndividualPoints()),
                        getOrEmpty(getOrEmpty(c.getChallenge().getTeamPoints())),
                        getOrEmpty(c.getCodeFrom())
                ));
            });
            writer.close();
        });
    }

    private String getOrEmpty(Object value) {
        return value != null ? value.toString() : "";
    }

    private void createCodeFormDialog(Set<ChallengeDto> challenges, ParticipantService participantService) {
        CodeForm codeForm = new CodeForm(challenges, participantService);
        codeFormDialog = new DialogForm<>(codeForm);
        codeFormDialog.setDeleteCallback(this::handleDelete);
        codeFormDialog.setSaveCallback(this::handleSave);
//        codeFormDialog.addValidator(createUniqueCodeValidator());
    }

    private void createGrid() {
        codesGrid = new Grid<>(CodeDto.class, false);
        codesGrid.addClassNames("code-grid");
        codesGrid.addColumn("code").setAutoWidth(true);
        codesGrid.addColumn("usages").setAutoWidth(true);
        codesGrid.addColumn("maxUsages").setAutoWidth(true);
        codesGrid.addColumn("active").setAutoWidth(true);
        codesGrid.addColumn("codeFrom").setAutoWidth(true);
        codesGrid.addColumn(getChallengeValueProvider()).setHeader("Challenge").setAutoWidth(true).setSortable(true);
        codesGrid.setAllRowsVisible(true);
        codesGrid.addItemClickListener(event -> codeFormDialog.openDialog(event.getItem()));
        updateGridItems();
    }

    private void updateGridItems() {
        codes = codeService.findAllByEvent(sessionService.getEvent());
        codes.sort(Comparator.comparing(c -> ((CodeDto) c).getChallenge().getName())
                .thenComparing(c -> ((CodeDto) c).getCode()));
        initialCodeIds = codes.stream()
                .map(CodeDto::getCode)
                .collect(Collectors.toSet());
        codesGrid.setItems(codes);
    }

    private void handleSave(CodeDto code) {
        code.setEvent(sessionService.getEvent());
        codeService.save(code);
        updateGridItems();
    }

    private void handleDelete(CodeDto code) {
        codeService.delete(code);
        updateGridItems();
    }

    private ValueProvider<CodeDto, String> getChallengeValueProvider() {
        return code -> Optional.ofNullable(code)
                .map(CodeDto::getChallenge)
                .map(ChallengeDto::getName)
                .orElse("---");
    }

    private CustomValidator<CodeDto> createUniqueCodeValidator() {
        return new CustomValidator<>(createValidatorPredicate(), createValidatorMessage());
    }

    private Predicate<CodeDto> createValidatorPredicate() {
        return code -> !initialCodeIds.contains(code.getCode());
    }

    private Function<CodeDto, String> createValidatorMessage() {
        return code -> "Code " + code.getCode() + " already exists";
    }
}
