package nl.kristalsoftware.datastore.base.eventstore;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.kristalsoftware.datastore.base.eventstore.event.EventLoader;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.UUIDBaseEventEntity;
import nl.kristalsoftware.domain.base.Aggregate;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEventStore<T extends Aggregate> {

    private final List<EventLoader> loaders;

    protected Map<Class<? extends UUIDBaseEventEntity>, EventLoader> eventLoaderMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (EventLoader eventLoader : loaders) {
            eventLoaderMap.put(eventLoader.appliesTo(), eventLoader);
        }
    }

    private List<UUIDBaseEventEntity> getEvents(T aggregateRoot) {
         return StreamSupport.stream(
                findAllByReference(aggregateRoot).spliterator(), false)
                    .collect(Collectors.toList());
    }

    protected void loadEvents(T aggregateRoot) {
        List<UUIDBaseEventEntity> events = getEvents(aggregateRoot);
        for (UUIDBaseEventEntity event : events) {
            eventLoaderMap.get(event.getClass()).loadEventData(aggregateRoot, event);
        }
    }

    protected T loadEvents(UUID reference, ApplicationEventPublisher applicationEventPublisher) {
        T aggregateRoot = createAggregateRoot(reference, applicationEventPublisher);
        List<UUIDBaseEventEntity> events = getEvents(aggregateRoot);
        for (UUIDBaseEventEntity event : events) {
            eventLoaderMap.get(event.getClass()).loadEventData(aggregateRoot, event);
        }
        return aggregateRoot;
    }


    protected abstract Iterable<UUIDBaseEventEntity> findAllByReference(T aggregateRoot);

    protected abstract T createAggregateRoot(UUID reference, ApplicationEventPublisher applicationEventPublisher);

    public abstract <V extends UUIDBaseEventEntity> void saveEventEntity(V eventEntity);
}
