package nl.kristalsoftware.datastore.base.messaging;

import lombok.extern.slf4j.Slf4j;
import nl.kristalsoftware.datastore.base.messaging.offsetmanagement.TopicPartitionHandler;
import nl.kristalsoftware.domain.base.DomainEventHandler;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EventConsumer<T extends SpecificRecord> {

    private final TopicPartitionHandler topicPartitionHandler;

    private final Map<String, DomainEventHandler<T>> eventMap;

    public EventConsumer(
            List<DomainEventHandler<T>> eventList,
            TopicPartitionHandler topicPartitionHandler) {
        eventMap = eventList.stream()
                .collect(Collectors.toMap(it -> it.appliesTo(), it -> it));
        this.topicPartitionHandler = topicPartitionHandler;
    }

    @Transactional
    protected void consumeData(ConsumerRecord<String, T> record, String eventId) {
        log.info("Key: {}, Value: {}, Partition: {}, Offset: {}",
                record.key(), record.value(), record.partition(), record.offset());
        DomainEventHandler<T> domainEventHandler = eventMap.get(eventId);
        domainEventHandler.saveEvent(record.value());
        topicPartitionHandler.save(record.topic(), record.partition(), record.offset());
    }

}
