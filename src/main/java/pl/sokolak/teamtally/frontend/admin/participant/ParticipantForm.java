package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.ValueProvider;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.participant.ParticipantService;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class ParticipantForm extends AbstractForm {

    private final ListBox<String> users = new ListBox<>();
    private final ParticipantService participantService;

    public ParticipantForm(ParticipantService participantService) {
        this.participantService = participantService;
        addClassName("participant-form");
        configureBinder();
        addFields();
    }

    private void configureBinder() {
        Binder<ParticipantDto> binder = new BeanValidationBinder<>(ParticipantDto.class);
        CheckboxGroup<String> pets = new CheckboxGroup<>();
        pets.setItems(participantService.findAll().stream().map(s -> s.getUser().getUsername()).collect(Collectors.toList()));
        binder.forField(pets).bind("user");
        setBinder(binder);
    }
}
