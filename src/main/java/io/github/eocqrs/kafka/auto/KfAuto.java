package io.github.eocqrs.kafka.auto;

import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.KfTypified;
import io.github.eocqrs.kafka.ProducerSettings;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.stream.Stream;

public final class KfAuto<K, X>
    implements ConsumerSettings<K, X>, ProducerSettings<K, X> {

    private final AutoNames names;
    private final Stream<KfTypified> params;

    public KfAuto(final AutoNames names, final KfTypified... params) {
        this.names = names;
        this.params = Stream.of(params);
    }

    @Override
    public KafkaConsumer<K, X> consumer() {
        return null;
    }

    @Override
    public KafkaProducer<K, X> producer() {
        return null;
    }
}
