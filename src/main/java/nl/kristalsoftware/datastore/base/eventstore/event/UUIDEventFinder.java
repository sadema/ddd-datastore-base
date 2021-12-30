package nl.kristalsoftware.datastore.base.eventstore.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.kristalsoftware.datastore.base.eventstore.UUIDEventStoreRepository;
import nl.kristalsoftware.datastore.base.eventstore.event.entity.UUIDBaseEventEntity;
import nl.kristalsoftware.domain.base.Aggregate;
import nl.kristalsoftware.domain.base.TinyUUIDType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Component
public class UUIDEventFinder<T extends Aggregate<? extends TinyUUIDType>> implements BaseEventFinder<T,UUIDBaseEventEntity> {

    private UUIDEventStoreRepository eventStoreRepository;

    @Autowired
    public void setEventStoreRepository(UUIDEventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
    }

    public List<UUIDBaseEventEntity> getEvents(T aggregateRoot) {
        return StreamSupport.stream(
                        findAllEventEntitiesByReference(aggregateRoot).spliterator(), false)
                .collect(Collectors.toList());
    }

    public Iterable<UUIDBaseEventEntity> findAllEventEntitiesByReference(T aggregateRoot) {
        return eventStoreRepository.findAllByReference(aggregateRoot.getReference().getValue());
    }

}
