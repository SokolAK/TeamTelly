package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class EventForm extends AbstractForm {

    private final TextField name = new TextField("Name");
    private final TextField personalPoints = new TextField("Date");
    private final TextField teamPoints = new TextField("Owner");

    public EventForm() {
        addClassName("event-form");
        configureBinder();
        addFields(name, personalPoints, teamPoints);
    }

    private void configureBinder() {
        Binder<ChallengeDto> binder = new BeanValidationBinder<>(ChallengeDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
