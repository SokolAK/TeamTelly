package pl.sokolak.teamtally.frontend.common.event;

import com.vaadin.flow.component.ComponentEvent;
import pl.sokolak.teamtally.frontend.common.AbstractForm;

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