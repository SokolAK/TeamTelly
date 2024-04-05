package pl.sokolak.teamtally.frontend.admin_section.event;

import com.vaadin.flow.component.ComponentEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;
import pl.sokolak.teamtally.frontend.common.SaveDeleteCancelAbstractForm;

public abstract class DataFormEvent extends ComponentEvent<AbstractForm> {
    private Object data;

    protected DataFormEvent(AbstractForm source, Object data) {
        super(source, false);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}