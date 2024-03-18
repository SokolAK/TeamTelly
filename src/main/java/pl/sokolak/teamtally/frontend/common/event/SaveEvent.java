package pl.sokolak.teamtally.frontend.common.event;

import pl.sokolak.teamtally.frontend.admin.event.DataFormEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class SaveEvent extends DataFormEvent {
    public SaveEvent(AbstractForm source, Object data) {
        super(source, data);
    }
}