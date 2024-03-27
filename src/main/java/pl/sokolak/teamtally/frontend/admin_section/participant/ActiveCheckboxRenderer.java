package pl.sokolak.teamtally.frontend.admin_section.participant;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sokolak.teamtally.backend.event.EventDto;
import pl.sokolak.teamtally.backend.participant.ParticipantDto;
import pl.sokolak.teamtally.backend.user.UserDto;

import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
class ActiveCheckboxRenderer {

    private final EventDto event;
    private final Consumer<UserDto> checkListener;
    private final Consumer<UserDto> uncheckListener;

    ComponentRenderer<CheckboxWithUser, UserDto> create() {
        return new ComponentRenderer<>(user ->
                new CheckboxWithUser(user, isParticipant(user), createCheckboxValueChangeListener()));
    }

    private boolean isParticipant(UserDto user) {
        return user.getParticipants().stream()
                .filter(ParticipantDto::isActive)
                .anyMatch(p -> event.equals(p.getEvent()));
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> createCheckboxValueChangeListener() {
        return event -> {
            UserDto user = getUserForCheckbox(event);
            Boolean isChecked = event.getValue();

            if (user == null || isChecked == null) {
                return;
            }

            if (isChecked) {
                checkListener.accept(user);
            } else {
                uncheckListener.accept(user);
            }
        };
    }

    private static UserDto getUserForCheckbox(AbstractField.ComponentValueChangeEvent<Checkbox, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(CheckboxWithUser.class::cast)
                .map(CheckboxWithUser::getUser)
                .orElse(null);
    }

    @Getter
    private static class CheckboxWithUser extends Checkbox {
        private final UserDto user;

        public CheckboxWithUser(UserDto user, boolean initialValue,
                                ValueChangeListener<ComponentValueChangeEvent<Checkbox, Boolean>> changeValueListener) {
            super(initialValue);
            this.user = user;
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-checkbox");
        }
    }
}
