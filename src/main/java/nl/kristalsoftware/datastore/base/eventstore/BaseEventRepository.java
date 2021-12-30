package nl.kristalsoftware.datastore.base.eventstore;

import nl.kristalsoftware.datastore.base.eventstore.event.entity.UUIDBaseEventEntity;

public interface BaseEventRepository<T> {
    Iterable<UUIDBaseEventEntity> findAllEventEntitiesByReference(T aggregateRoot);
}
