package pl.sokolak.teamtally.backend.session;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.slf4j.LoggerFactory;

public class ServiceListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new Session has been initialized!"));

        event.getSource().addUIInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new UI has been initialized!"));
    }
}