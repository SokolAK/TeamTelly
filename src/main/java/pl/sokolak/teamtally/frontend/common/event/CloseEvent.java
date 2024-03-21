package pl.sokolak.teamtally.frontend.common.event;

import pl.sokolak.teamtally.frontend.admin_section.event.DataFormEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

public class CloseEvent extends DataFormEvent {
    public CloseEvent(AbstractForm source) {
        super(source, null);
    }
}
