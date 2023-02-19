package org.eocqrs.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since $0.id
 */
public interface Producer<K, X> {

  void send(K key, Data<X> message) throws Exception;

  final class S<T> implements Producer<String, T> {

    KafkaProducer<String, T> origin;

    @Override
    public void send(final String key, final Data<T> data) throws Exception {
      this.origin.send(
        new ProducerRecord<>(
          data.topic(),
          data.partition(),
          key,
/**
 * @todo #1:30m/DEV KfData implementation
 *
 */
          new DataizeOf<>(data.dataized(), "text")
            .dataize()
        )
      );
    }
  }
}