package nl.kristalsoftware.datastore.base.eventstore;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.kristalsoftware.datastore.base.eventstore.event.EventHandler;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.BaseEventEntity;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.EventHandlerProvider;
import nl.kristalsoftware.domain.base.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseAggregateLoader<T extends Aggregate> {

    private EventHandlerProvider eventHandlerProvider;

    private EventStoreRepository eventStoreRepository;

    @Autowired
    public final void setEventHandlerProvider(EventHandlerProvider eventHandlerProvider) {
        this.eventHandlerProvider = eventHandlerProvider;
    }

    @Autowired
    public final void setEventStoreRepository(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
    }

    private List<BaseEventEntity> getEvents(T aggregateRoot) {
         return StreamSupport.stream(
                         findAllEventEntitiesByReference(aggregateRoot).spliterator(), false)
                    .collect(Collectors.toList());
    }

    private void loadEvents(T aggregateRoot) {
        List<BaseEventEntity> eventEntities = getEvents(aggregateRoot);
        for (BaseEventEntity eventEntity : eventEntities) {
            EventHandler eventHandler = eventHandlerProvider.getEventHandler(eventEntity.getDomainEventName());
            eventHandler.loadEventData(aggregateRoot, eventEntity);
        }
    }

    public T loadEvents(UUID reference, ApplicationEventPublisher applicationEventPublisher) {
        T aggregateRoot = createAggregateRoot(reference, applicationEventPublisher);
        loadEvents(aggregateRoot);
        return aggregateRoot;
    }

    public Iterable<BaseEventEntity> findAllEventEntitiesByReference(T aggregateRoot) {
        return eventStoreRepository.findAllByReference(aggregateRoot.getReferenceValue());
    }

    protected abstract T createAggregateRoot(UUID reference, ApplicationEventPublisher applicationEventPublisher);

}
