//package pl.sokolak.teamtally.frontend.admin_section.code;
//
//import com.vaadin.flow.component.checkbox.Checkbox;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.BeanValidationBinder;
//import pl.sokolak.teamtally.abstracts.Data;
//import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
//import pl.sokolak.teamtally.backend.code.CodeDto;
//import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
//import pl.sokolak.teamtally.backend.participant.ParticipantService;
//import pl.sokolak.teamtally.frontend.common.dialog.DialogForm;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static pl.sokolak.teamtally.frontend.localization.Translator.t;
//
//class CodeFormCopy extends DialogForm {
//
//    private final TextField code = new TextField("Code");
//    private final TextField maxUsages = new TextField("Max usages (∞ if empty)");
//    private final Checkbox active = new Checkbox("Active");
//    private final ComboBox<ChallengeDto> challenge = new ComboBox<>("Challenge");
//    private final TextField codeFrom = new TextField("Code from");
//
//    private final Grid<ParticipantDataView> participants = new Grid<>(ParticipantDataView.class);
//    private final ParticipantService participantService;
//
//    public CodeFormCopy(Set<ChallengeDto> challenges, ParticipantService participantService) {
//        this.participantService = participantService;
//        addClassName("code-form");
//        configureBinder();
//        challenge.setItems(sort(challenges));
//        challenge.setItemLabelGenerator(ChallengeDto::getName);
//
//        add(code, maxUsages, challenge, codeFrom, active, createParticipantTable());
//    }
//
//    private List<ChallengeDto> sort(Set<ChallengeDto> challenges) {
//        return challenges.stream()
//                .sorted(Comparator.comparing(ChallengeDto::getName))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    protected void configureBinder() {
//        binder = new BeanValidationBinder<>(CodeDto.class);
//        binder.bindInstanceFields(this);
//        binder.forField(code)
//                .asRequired(t("validation.notnull.message"))
//                .bind("code");
//        binder.forField(challenge)
//                .asRequired(t("validation.notnull.message"))
//                .bind("challenge");
//    }
//
//    @Override
//    public void setData(Data data) {
//        binder.setBean(data);
//        Set<ParticipantDataView> participantDataViews = Optional.ofNullable(data)
//                .map(CodeDto.class::cast)
//                .map(this::getParticipants)
//                .orElse(Collections.emptySet());
//        participants.setItems(participantDataViews);
//        if(participantDataViews.isEmpty()) {
////            code.setReadOnly(false);
//            maxUsages.setReadOnly(false);
//            challenge.setReadOnly(false);
//            active.setReadOnly(false);
//        } else {
////            code.setReadOnly(true);
//            maxUsages.setReadOnly(true);
//            challenge.setReadOnly(true);
//            active.setReadOnly(true);
//        }
//    }
//
//    private Set<ParticipantDataView> getParticipants(CodeDto code) {
//        return participantService.findUsernamesByCode(code);
//    }
//
//    private VerticalLayout createParticipantTable() {
//        participants.setColumns();
//        participants.addColumn("username").setHeader("Used by");
//        participants.setAllRowsVisible(true);
//        return new VerticalLayout(participants);
//    }
//}
