package pl.sokolak.teamtally.frontend.admin.event;

import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class DeleteEvent extends DataFormEvent {
    public DeleteEvent(AbstractForm source, Object data) {
        super(source, data);
    }

}