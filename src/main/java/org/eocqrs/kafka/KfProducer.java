package org.eocqrs.kafka;

import lombok.AllArgsConstructor;
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

  @AllArgsConstructor
  @lombok.Data
  final class User {

    private final Long id;
    private final String name;
  }

  @RequiredArgsConstructor
  final class A {

    private final KfProducer<String, User> producer;

    void test() {
      final User user = new User(1L, "h1alexbel");
      this.producer.send(user.getName(), new KfData<>(user, "users", 1));
    }
  }
}