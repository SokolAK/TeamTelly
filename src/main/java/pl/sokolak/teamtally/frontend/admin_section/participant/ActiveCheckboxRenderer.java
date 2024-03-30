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

import java.util.Optional;
import java.util.function.Consumer;

@AllArgsConstructor
class ActiveCheckboxRenderer {

    private final EventDto event;
    private final Consumer<ParticipantDto> checkListener;
    private final Consumer<ParticipantDto> uncheckListener;

    ComponentRenderer<CheckboxWithParticipant, ParticipantDto> create() {
        return new ComponentRenderer<>(participant ->
                new CheckboxWithParticipant(participant, createCheckboxValueChangeListener()));
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Checkbox, Boolean>> createCheckboxValueChangeListener() {
        return event -> {
            ParticipantDto participant = getParticipantForCheckbox(event);
            Boolean isChecked = event.getValue();
            if (isChecked) {
                checkListener.accept(participant);
            } else {
                uncheckListener.accept(participant);
            }
        };
    }

    private static ParticipantDto getParticipantForCheckbox(AbstractField.ComponentValueChangeEvent<Checkbox, ?> event) {
        return Optional.ofNullable(event)
                .map(ComponentEvent::getSource)
                .map(CheckboxWithParticipant.class::cast)
                .map(CheckboxWithParticipant::getParticipant)
                .orElse(null);
    }

    @Getter
    private static class CheckboxWithParticipant extends Checkbox {
        private final ParticipantDto participant;

        public CheckboxWithParticipant(ParticipantDto participant,
                                       ValueChangeListener<ComponentValueChangeEvent<Checkbox, Boolean>> changeValueListener) {
            super(participant.isActive());
            this.participant = participant;
            this.addValueChangeListener(changeValueListener);
            this.addClassName("participant-checkbox");
        }
    }
}
