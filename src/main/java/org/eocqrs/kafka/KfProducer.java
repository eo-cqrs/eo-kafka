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

  private final KafkaProducer<K, X> origin;

  /**
   * @todo #30:60m/DEV it for data consumption
   */
  @Override
  public void send(final K key, final Data<X> data) {
    this.origin.send(
        new ProducerRecord<>(
          data.topic(),
          data.partition(),
          key,
          data.dataized()
            .dataize()
        )
      );
  }
}