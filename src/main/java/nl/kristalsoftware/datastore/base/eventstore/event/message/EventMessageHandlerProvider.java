package nl.kristalsoftware.datastore.base.eventstore.event.message;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventMessageHandlerProvider {

    protected Map<String, EventMessageHandler<?>> eventMessageMap;

    public EventMessageHandlerProvider(List<EventMessageHandler<?>> eventMessageHanlers) {
        eventMessageMap = eventMessageHanlers.stream()
                .collect(Collectors.toMap(it -> it.appliesTo().getSimpleName(), it -> it));
    }

    public EventMessageHandler<?> getEventMessageHandler(String eventId) {
        return eventMessageMap.get(eventId);
    }

}
