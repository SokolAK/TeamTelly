package pl.sokolak.teamtally.frontend.admin.team;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pl.sokolak.teamtally.backend.team.TeamDto;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

class TeamForm extends AbstractForm {

    private final TextField name = new TextField("Name");
    private final TextField personalPoints = new TextField("Personal points");
    private final TextField teamPoints = new TextField("Team points");

    public TeamForm() {
        addClassName("team-form");
        configureBinder();
        addFields(name, personalPoints, teamPoints);
    }

    private void configureBinder() {
        Binder<TeamDto> binder = new BeanValidationBinder<>(TeamDto.class);
        binder.bindInstanceFields(this);
        setBinder(binder);
    }
}
