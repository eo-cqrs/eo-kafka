package org.eocqrs.kafka;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public final class KfProducer<K, X> implements Producer<K, X> {

  private final KafkaProducer<K, X> producer;
  private final ProducerSettings settings;

  void struct() {
    final Map<String, Object> map = new HashMap<>();
    map.put("bootstrap.servers", this.settings.bootstrapServers());
    map.put("key.serializer", this.settings.keyClass());
    map.put("value.serializer", this.settings.valueClass());
    final KafkaProducer<K, X> origin = new KafkaProducer<>(map);
  }

  /**
   * @todo #21:30m/DEV send
   */
  @Override
  public void send(final K key, final Data<X> data) {
    this.producer.send(
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