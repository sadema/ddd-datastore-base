package nl.kristalsoftware.datastore.base.eventstore;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nl.kristalsoftware.datastore.base.eventstore.event.EventLoader;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.BaseEventEntity;
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

    protected Map<Class<? extends BaseEventEntity>, EventLoader> eventLoaderMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (EventLoader eventLoader : loaders) {
            eventLoaderMap.put(eventLoader.appliesTo(), eventLoader);
        }
    }

    private List<BaseEventEntity> getEvents(T aggregateRoot) {
         return StreamSupport.stream(
                findAllByReference(aggregateRoot).spliterator(), false)
                    .collect(Collectors.toList());
    }

    protected void loadEvents(T aggregateRoot) {
        List<BaseEventEntity> events = getEvents(aggregateRoot);
        for (BaseEventEntity event : events) {
            eventLoaderMap.get(event.getClass()).loadEventData(aggregateRoot, event);
        }
    }

    protected T loadEvents(UUID reference, ApplicationEventPublisher applicationEventPublisher) {
        T aggregateRoot = createAggregateRoot(reference, applicationEventPublisher);
        List<BaseEventEntity> events = getEvents(aggregateRoot);
        for (BaseEventEntity event : events) {
            eventLoaderMap.get(event.getClass()).loadEventData(aggregateRoot, event);
        }
        return aggregateRoot;
    }


    protected abstract Iterable<BaseEventEntity> findAllByReference(T aggregateRoot);

    protected abstract T createAggregateRoot(UUID reference, ApplicationEventPublisher applicationEventPublisher);

    public abstract <V extends BaseEventEntity> void saveEventEntity(V eventEntity);
}
