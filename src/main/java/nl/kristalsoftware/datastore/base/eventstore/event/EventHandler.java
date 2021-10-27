package nl.kristalsoftware.datastore.base.eventstore.event;

import nl.kristalsoftware.domain.base.BaseEvent;

public interface EventHandler<U,T> {

    Class<? extends BaseEvent> appliesTo();

    void loadEventData(U aggregate, T eventEntity);

}
