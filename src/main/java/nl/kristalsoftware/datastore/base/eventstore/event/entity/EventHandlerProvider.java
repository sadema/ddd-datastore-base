package nl.kristalsoftware.datastore.base.eventstore.event.entity;

import nl.kristalsoftware.datastore.base.eventstore.event.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventHandlerProvider {

    protected Map<String, EventHandler<?,?>> eventHandlerMap;

    public EventHandlerProvider(List<EventHandler<?,?>> eventLoaders) {
        eventHandlerMap = eventLoaders.stream()
                .collect(Collectors.toMap(it -> it.appliesTo().getSimpleName(), it -> it));
    }

    public EventHandler<?,?> getEventHandler(String eventId) {
        return eventHandlerMap.get(eventId);
    }

}
