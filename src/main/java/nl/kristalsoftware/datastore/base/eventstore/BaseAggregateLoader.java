package nl.kristalsoftware.datastore.base.eventstore;

import nl.kristalsoftware.datastore.base.eventstore.event.BaseEventFinder;
import nl.kristalsoftware.datastore.base.eventstore.event.EventHandler;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.BaseEventEntityName;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.EventHandlerProvider;
import nl.kristalsoftware.domain.base.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseAggregateLoader<T extends Aggregate, V extends BaseEventEntityName> {

    private final BaseEventFinder<T,V> baseEventFinder;

    private EventHandlerProvider eventHandlerProvider;

    @Autowired
    public final void setEventHandlerProvider(EventHandlerProvider eventHandlerProvider) {
        this.eventHandlerProvider = eventHandlerProvider;
    }

    public BaseAggregateLoader(BaseEventFinder<T,V> baseEventFinder) {
        this.baseEventFinder = baseEventFinder;
    }

    public void loadEvents(T aggregateRoot) {
        List<V> eventEntities = baseEventFinder.getEvents(aggregateRoot);
        aggregateRoot.setNumberOfEvents(eventEntities.size());
        for (V eventEntity : eventEntities) {
            EventHandler eventHandler = eventHandlerProvider.getEventHandler(eventEntity.getDomainEventName());
            eventHandler.loadEventData(aggregateRoot, eventEntity);
        }
    }

}
