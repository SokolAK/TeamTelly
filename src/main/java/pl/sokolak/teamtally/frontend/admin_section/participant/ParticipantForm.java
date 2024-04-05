package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.compress.utils.Sets;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.UserDto;
import pl.sokolak.teamtally.backend.user.UserService;
import pl.sokolak.teamtally.frontend.common.SaveCancelAbstractForm;
import pl.sokolak.teamtally.frontend.common.event.SaveEvent;

import java.util.Collections;
import java.util.Set;

class ParticipantForm extends SaveCancelAbstractForm {

    private final EventDto event;
    private final UserService userService;
    private final MultiSelectComboBox<UserDto> users = new MultiSelectComboBox<>("Not assigned users");
    private final TextArea emails = new TextArea("Allowed emails");

    public ParticipantForm(EventDto event, UserService userService) {
        this.event = event;
        this.userService = userService;
        addClassName("participant-form");
        configureBinder(); //TODO binder niepotrzebny
        addFields(users, emails);
        users.setItemLabelGenerator(UserDto::toString);
        init();
    }

    @Override
    public void init() {
        setNonParticipantUsers();
        emails.clear();
    }

    private void configureBinder() {
        Binder<ParticipantDto> binder = new BeanValidationBinder<>(ParticipantDto.class);
        setBinder(binder);
    }

    private void setNonParticipantUsers() {
        users.setItems(userService.findAll().stream()
                .filter(u -> u.getParticipantForEvent(event).isEmpty())
                .toList());
    }

//    private void setAllUsers() {
//        users.setItems(usersList);
//        users.select(usersList.stream()
//                .filter(u -> u.getParticipantForEvent(event).isPresent())
//                .toArray(UserDto[]::new));
//        addFields(users);
//    }

    @Override
    protected void validateAndSave() {
        fireEvent(new SaveEvent(this, new ParticipantsToAdd(users.getSelectedItems(), getEmailSet())));
    }

    private Set<String> getEmailSet() {
        String emailsFieldValue = emails.getValue();
        if (emailsFieldValue == null || emailsFieldValue.isEmpty()) {
            return Collections.emptySet();
        }
        return Sets.newHashSet(emailsFieldValue.split(";"));
    }

    record ParticipantsToAdd(Set<UserDto> users, Set<String> emails) {
    }
}
