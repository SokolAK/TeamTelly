package pl.sokolak.teamtally.frontend.admin.participant;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class ParticipantForm extends AbstractForm {

    private final TextField name = new TextField("Name");
    private final TextField personalPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");

    public ParticipantForm() {
        addClassName("challenge-form");
        configureBinder();
        addFields(name, personalPoints, teamPoints);
    }

    private void configureBinder() {
        Binder<ChallengeDto> binder = new BeanValidationBinder<>(ChallengeDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
