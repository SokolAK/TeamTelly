package pl.sokolak.teamtally.frontend.admin_section.challenge;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.challenge.ChallengeDto;
import pl.sokolak.teamtally.backend.tag.TagDto;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

import java.util.Set;

class ChallengeForm extends SaveDeleteCancelAbstractForm {

    private final TextField name = new TextField("Name");
    private final TextField individualPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");
    private final MultiSelectComboBox<TagDto> tags = new MultiSelectComboBox<>("Tags");

    public ChallengeForm(Set<TagDto> tagsForEvent) {
        addClassName("challenge-form");
        configureBinder();
        tags.setItems(tagsForEvent);
        tags.setItemLabelGenerator(TagDto::getName);
        addFields(name, individualPoints, teamPoints, tags);
    }

    private void configureBinder() {
        Binder<ChallengeDto> binder = new BeanValidationBinder<>(ChallengeDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
