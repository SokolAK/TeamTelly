//package pl.sokolak.teamtally.frontend.admin_section.code;
//
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.function.ValueProvider;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.dao.DataIntegrityViolationException;
//import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
//import pl.sokolak.teamtally.backend.challenge.ChallengeService;
//import pl.sokolak.teamtally.backend.code.CodeDto;
//import pl.sokolak.teamtally.backend.code.CodeService;
//import pl.sokolak.teamtally.backend.participant.ParticipantService;
//import pl.sokolak.teamtally.backend.session.SessionService;
//import pl.sokolak.teamtally.frontend.MainView;
//import pl.sokolak.teamtally.frontend.common.NotificationService;
//import pl.sokolak.teamtally.frontend.common.dialog.AbstractViewWithDialog;
//import pl.sokolak.teamtally.frontend.common.event.SaveEvent;
//
//import java.util.*;
//
//@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@RolesAllowed("ADMIN")
//@Route(value = "codes", layout = MainView.class)
//@PageTitle("Codes")
//public class CodeViewCopy extends AbstractViewWithDialog<CodeDto> {
//
//    private CodeForm codeForm;
//
//    public CodeViewCopy(CodeService codeService, ChallengeService challengeService, ParticipantService participantService, SessionService sessionService) {
//        this.sessionService = sessionService;
//        this.service = codeService;
//        Set<ChallengeDto> challenges = challengeService.findAllDataByEvent(sessionService.getEvent());
//        this.codeForm = new CodeForm(challenges, participantService);
//        addClassName("code-view");
//        init();
//    }
//
//    @Override
//    protected void configureGrid() {
//        grid = new Grid<>(CodeDto.class, false);
//        grid.addClassNames("code-grid");
//        grid.addColumn("code").setAutoWidth(true);
//        grid.addColumn("usages").setAutoWidth(true);
//        grid.addColumn("maxUsages").setAutoWidth(true);
//        grid.addColumn("active").setAutoWidth(true);
//        grid.addColumn(getChallengeValueProvider()).setHeader("Challenge").setAutoWidth(true);
//        grid.setAllRowsVisible(true);
//        grid.asSingleSelect().addValueChangeListener(event -> editData(event.getValue()));
//    }
//
//    private ValueProvider<CodeDto, String> getChallengeValueProvider() {
//        return code -> Optional.ofNullable(code)
//                .map(CodeDto::getChallenge)
//                .map(ChallengeDto::getName)
//                .orElse("---");
//    }
//
//    @Override
//    protected CodeDto emptyData() {
//        return CodeDto.builder().active(true).build();
//    }
//
//    @Override
//    protected List<CodeDto> fetchData() {
//        return new ArrayList<>(((CodeService) service).findAllByEvent(sessionService.getEvent()));
//    }
//
//    @Override
//    protected Comparator<CodeDto> getComparator() {
//        return Comparator.comparing(c -> c.getChallenge().getName());
//    }
//
//    @Override
//    protected void configureFormCommon() {
//        form.addSaveListener(event -> {
//            try {
//                saveOrUpdateData(event);
//                NotificationService.showSuccess("Challenge saved");
//                closeEditorAndUpdateList();
//            } catch (DataIntegrityViolationException e) {
//                String code = ((CodeDto) event.getData()).getCode();
//                NotificationService.showError("Code " + code + " already exists!");
//            }
//        });
//        form.addDeleteListener(this::deleteData);
//        form.addCloseListener(e -> closeEditor());
//    }
//
//    @Override
//    protected void saveData(SaveEvent event) {
//        CodeDto code = (CodeDto) event.getData();
//        code.setEvent(sessionService.getEvent());
//        service.save(code);
//    }
//}
