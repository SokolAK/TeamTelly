package pl.sokolak.teamtally.frontend.common.event;

import pl.sokolak.teamtally.frontend.admin_section.event.DataFormEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

public class SaveEvent extends DataFormEvent {
    public SaveEvent(AbstractForm source, Object data) {
        super(source, data);
    }
}