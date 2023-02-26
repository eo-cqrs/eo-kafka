package org.eocqrs.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
public interface ConsumerSettings<K, X> {

  KafkaConsumer<K, X> consumer();
}