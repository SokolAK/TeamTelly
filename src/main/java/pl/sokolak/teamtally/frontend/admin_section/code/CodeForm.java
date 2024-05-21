package pl.sokolak.teamtally.frontend.admin_section.code;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

import java.util.*;
import java.util.stream.Collectors;

class CodeForm extends SaveDeleteCancelAbstractForm {

    private final TextField code = new TextField("Code");
    private final TextField maxUsages = new TextField("Max usages (∞ if empty)");
    private final Checkbox active = new Checkbox("Active");
    private final ComboBox<ChallengeDto> challenge = new ComboBox<>("Challenge");
    private final Grid<ParticipantDataView> participants = new Grid<>(ParticipantDataView.class);

    private final ParticipantService participantService;

    public CodeForm(Set<ChallengeDto> challenges, ParticipantService participantService) {
        this.participantService = participantService;
        addClassName("code-form");
        configureBinder();
        challenge.setItems(sort(challenges));
        challenge.setItemLabelGenerator(ChallengeDto::getName);

        addFields(code, maxUsages, challenge, active, createParticipantTable());
    }

    private List<ChallengeDto> sort(Set<ChallengeDto> challenges) {
        return challenges.stream()
                .sorted(Comparator.comparing(ChallengeDto::getName))
                .collect(Collectors.toList());
    }

    private void configureBinder() {
        Binder<CodeDto> binder = new BeanValidationBinder<>(CodeDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }

    @Override
    public void setData(Data data) {
        binder.setBean(data);
        Set<ParticipantDataView> participantDataViews = Optional.ofNullable(data)
                .map(CodeDto.class::cast)
                .map(this::getParticipants)
                .orElse(Collections.emptySet());
        participants.setItems(participantDataViews);
        if(participantDataViews.isEmpty()) {
            code.setReadOnly(false);
            maxUsages.setReadOnly(false);
            challenge.setReadOnly(false);
            active.setReadOnly(false);
        } else {
            code.setReadOnly(true);
            maxUsages.setReadOnly(true);
            challenge.setReadOnly(true);
            active.setReadOnly(true);
        }
    }

    private Set<ParticipantDataView> getParticipants(CodeDto code) {
        return participantService.findUsernamesByCode(code);
    }

    private VerticalLayout createParticipantTable() {
        participants.setColumns();
        participants.addColumn("username").setHeader("Used by");
        participants.setAllRowsVisible(true);
        return new VerticalLayout(participants);
    }
}
