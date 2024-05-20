package pl.sokolak.teamtally.backend.util;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.function.Consumer;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventBus {

    private Map<String, List<Consumer<Object>>> listeners = new HashMap<>();

    public void push(String channel, Object object) {
        Optional.ofNullable(listeners.get(channel))
                .orElse(Collections.emptyList())
                .forEach(consumer -> consumer.accept(object));
    }

    public void addListener(String channel, Consumer<Object> consumer) {
        listeners.computeIfAbsent(channel, k -> new ArrayList<>());
        listeners.get(channel).add(consumer);
    }
}
