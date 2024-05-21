package pl.sokolak.teamtally.backend.util;

import com.vaadin.flow.component.UI;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventBus {

    private final Map<String, List<Consumer<Object>>> listeners = new HashMap<>();

//    public EventBus(UI ui) {
//        this.ui = ui;
//    }

    public void push(String channel, Object object) {
        Optional.ofNullable(listeners.get(channel))
                .orElse(Collections.emptyList())
                .forEach(consumer -> UI.getCurrent().access(() -> consumer.accept(object)));
    }

    public void addListener(String channel, Consumer<Object> consumer) {
        listeners.computeIfAbsent(channel, k -> new ArrayList<>());
        listeners.get(channel).add(consumer);
    }
}
