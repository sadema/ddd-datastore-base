package nl.kristalsoftware.datastore.base.messaging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kristalsoftware.datastore.base.eventstore.event.message.EventMessageHandler;
import nl.kristalsoftware.datastore.base.messaging.offsetmanagement.TopicPartitionHandler;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventConsumer<T extends SpecificRecord> {

    private TopicPartitionHandler topicPartitionHandler;

    @Autowired
    public final void setTopicPartitionHandler(TopicPartitionHandler topicPartitionHandler) {
        this.topicPartitionHandler = topicPartitionHandler;
    }

    @Transactional
    protected void consumeData(EventMessageHandler<T> eventMessageHandler, ConsumerRecord<String, T> record) {
        log.info("Key: {}, Value: {}, Partition: {}, Offset: {}",
                record.key(), record.value(), record.partition(), record.offset());
        eventMessageHandler.save(record.value());
        topicPartitionHandler.save(record.topic(), record.partition(), record.offset());
    }

}
