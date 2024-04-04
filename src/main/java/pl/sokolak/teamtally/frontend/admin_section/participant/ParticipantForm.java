package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

class ParticipantForm extends AbstractForm {

    private final TextField userDto = new TextField("UserDto");

    public ParticipantForm() {
        addClassName("user-form");
        configureBinder();
        addFields(userDto);
    }

    private void configureBinder() {
        Binder<ParticipantDto> binder = new BeanValidationBinder<>(ParticipantDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
