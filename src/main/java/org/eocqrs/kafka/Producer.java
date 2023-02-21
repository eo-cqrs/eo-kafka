package org.eocqrs.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since $0.id
 */
public interface Producer<K, X> {

  void send(K key, Data<X> message) throws Exception;

  @RequiredArgsConstructor
  final class S<T> implements Producer<String, T> {

    private final KafkaProducer<String, T> origin;

    @Override
    public void send(final String key, final Data<T> data) throws Exception {
      this.origin.send(
        new ProducerRecord<>(
          data.topic(),
          data.partition(),
          key,
          new DataizedOf<>(data.dataized(), "text")
            .dataize()
        )
      );
    }
  }
}