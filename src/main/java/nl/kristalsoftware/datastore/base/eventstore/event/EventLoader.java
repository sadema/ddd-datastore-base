package nl.kristalsoftware.datastore.base.eventstore.event;

import nl.kristalsoftware.datastore.base.eventstore.event.entity.BaseEventEntity;

public interface EventLoader<U,T> {

    Class<? extends BaseEventEntity> appliesTo();

    void loadEventData(U aggregate, T eventEntity);

}
