package pl.sokolak.teamtally.frontend.admin.event;

import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class SaveEvent extends DataFormEvent {
    public SaveEvent(AbstractForm source, Object data) {
        super(source, data);
    }
}