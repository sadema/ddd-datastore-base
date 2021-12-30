package nl.kristalsoftware.datastore.base.eventstore.event;

import nl.kristalsoftware.datastore.base.eventstore.event.entity.UUIDBaseEventEntity;

public interface EventLoader<U,T> {

    Class<? extends UUIDBaseEventEntity> appliesTo();

    void loadEventData(U aggregate, T eventEntity);

}
