package pl.sokolak.teamtally.frontend.admin.event;

import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class CloseEvent extends DataFormEvent {
    public CloseEvent(AbstractForm source) {
        super(source, null);
    }
}
