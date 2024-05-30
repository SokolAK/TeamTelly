package pl.sokolak.teamtally.frontend.admin_section.code;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.frontend.common.dialog.AbstractForm;

import java.util.*;
import java.util.stream.Collectors;

import static pl.sokolak.teamtally.frontend.localization.Translator.t;

class CodeForm extends AbstractForm<CodeDto> {

    private final TextField code = new TextField("Code");
    private final TextField maxUsages = new TextField("Max usages (∞ if empty)");
    private final Checkbox active = new Checkbox("Active");
    private final ComboBox<ChallengeDto> challenge = new ComboBox<>("Challenge");
    private final TextField codeFrom = new TextField("Code from");

    private final Grid<ParticipantDataView> participants = new Grid<>(ParticipantDataView.class);
    private final ParticipantService participantService;

    public CodeForm(Set<ChallengeDto> challenges, ParticipantService participantService) {
        super(CodeDto.class);
        this.participantService = participantService;
        addClassName("code-form");
        configureBinder();

        challenge.setItems(sort(challenges));
        challenge.setItemLabelGenerator(ChallengeDto::getName);

        add(code, maxUsages, challenge, codeFrom, active, createParticipantTable());
    }

    private void configureBinder() {
        binder.bindInstanceFields(this);
        binder.forField(code)
                .asRequired(t("validation.notnull.message"))
                .bind("code");
        binder.forField(challenge)
                .asRequired(t("validation.notnull.message"))
                .bind("challenge");
    }

    private List<ChallengeDto> sort(Set<ChallengeDto> challenges) {
        return challenges.stream()
                .sorted(Comparator.comparing(ChallengeDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void setData(CodeDto data) {
        binder.setBean(data);
        Set<ParticipantDataView> participantDataViews = Optional.ofNullable(data)
                .map(participantService::findUsernamesByCode)
                .orElse(Collections.emptySet());
        participants.setItems(participantDataViews);
        if(participantDataViews.isEmpty()) {
            maxUsages.setReadOnly(false);
            challenge.setReadOnly(false);
            active.setReadOnly(false);
        } else {
            maxUsages.setReadOnly(true);
            challenge.setReadOnly(true);
            active.setReadOnly(true);
        }
    }

    private VerticalLayout createParticipantTable() {
        participants.setColumns();
        participants.addColumn("username").setHeader("Used by");
        participants.setAllRowsVisible(true);
        return new VerticalLayout(participants);
    }
}
