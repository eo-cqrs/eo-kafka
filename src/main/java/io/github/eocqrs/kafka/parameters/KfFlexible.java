package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.ProducerSettings;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @todo #147:30m/DEV Opportunity for XML configuration
 * We have to implement creating of settings from XML file.
 */
/**
 * @todo #147:30m/DEV Update documentation
 * Add use-cases to README for KfFlexible.
 */
/**
 * KfFlexibleSettings allow you to add custom settings.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
public final class KfFlexible<K, X>
  implements ConsumerSettings<K, X>, ProducerSettings<K, X> {

  private final Params params;

  @Override
  public KafkaConsumer<K, X> consumer() {
    return new KafkaConsumer<>(new MapParams(this.params).value());
  }

  @Override
  public KafkaProducer<K, X> producer() {
    return new KafkaProducer<>(new MapParams(this.params).value());
  }
}
