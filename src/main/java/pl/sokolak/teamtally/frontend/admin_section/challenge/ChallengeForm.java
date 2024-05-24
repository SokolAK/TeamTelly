package pl.sokolak.teamtally.frontend.admin_section.challenge;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.abstracts.Data;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.code.CodeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDataView;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.tag.TagDto;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

class ChallengeForm extends SaveDeleteCancelAbstractForm {

    private final TextField name = new TextField("Name");
    private final TextArea description = new TextArea("Description");
    private final TextField individualPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");
    private final MultiSelectComboBox<TagDto> tags = new MultiSelectComboBox<>("Tags");
    private final Grid<ParticipantDataView> participants = new Grid<>(ParticipantDataView.class);
    private final ParticipantService participantService;

    public ChallengeForm(Set<TagDto> tagsForEvent, ParticipantService participantService) {
        this.participantService = participantService;
        addClassName("challenge-form");
        configureBinder();
        tags.setItems(tagsForEvent);
        tags.setItemLabelGenerator(TagDto::getName);
        addFields(name, description, individualPoints, teamPoints, createParticipantTable(), tags);
    }

    @Override
    public void setData(Data data) {
        binder.setBean(data);
        Set<ParticipantDataView> participantDataViews = Optional.ofNullable(data)
                .map(ChallengeDto.class::cast)
                .map(this::getParticipants)
                .orElse(Collections.emptySet());
        participants.setItems(participantDataViews);
    }

    private void configureBinder() {
        Binder<ChallengeDto> binder = new BeanValidationBinder<>(ChallengeDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }

    private Set<ParticipantDataView> getParticipants(ChallengeDto challenge) {
        return participantService.findUsernamesByChallenge(challenge);
    }

    private VerticalLayout createParticipantTable() {
        participants.setColumns();
        participants.addColumn("username").setHeader("Completed by");
        participants.setAllRowsVisible(true);
        return new VerticalLayout(participants);
    }
}
