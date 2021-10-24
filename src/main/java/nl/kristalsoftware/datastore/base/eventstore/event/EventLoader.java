package nl.kristalsoftware.datastore.base.eventstore.event;

public interface EventLoader<U,T> {

    Class<? extends BaseEventEntity> appliesTo();

    void loadEventData(U aggregate, T eventEntity);

}
