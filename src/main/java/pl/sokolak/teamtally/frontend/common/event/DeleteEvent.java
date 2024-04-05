package pl.sokolak.teamtally.frontend.common.event;

import pl.sokolak.teamtally.frontend.admin_section.event.DataFormEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

public class DeleteEvent extends DataFormEvent {
    public DeleteEvent(AbstractForm source, Object data) {
        super(source, data);
    }

}