package pl.sokolak.teamtally.frontend.admin.event;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class EventForm extends AbstractForm {

    private final TextField name = new TextField("Name");
    private final DatePicker startDate = new DatePicker("Start");
    private final DatePicker endDate = new DatePicker("End");

    public EventForm() {
        addClassName("event-form");
        configureBinder();
        addFields(name, startDate, endDate);
    }

    private void configureBinder() {
        Binder<EventDto> binder = new BeanValidationBinder<>(EventDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
