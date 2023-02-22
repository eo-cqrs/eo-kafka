package org.eocqrs.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
public interface ProducerSettings<K, X> {

  KafkaProducer<K, X> producer();
}