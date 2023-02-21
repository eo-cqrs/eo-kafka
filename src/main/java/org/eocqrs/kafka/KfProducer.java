package org.eocqrs.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public final class KfProducer<K, X> implements Producer<K, X> {

  private final KafkaProducer<K, X> producer;

  /**
   * @todo #21:30m/DEV send
   */
  @Override
  public void send(final K key, final Data<X> message) {
    this.producer.send(
      new ProducerRecord<>(
        message.topic(),
        message.partition(),
        key,
        message.dataized()
          .dataize()
      )
    );
  }
}