package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.ParamsAttr;
import io.github.eocqrs.kafka.ProducerSettings;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;
import java.util.stream.Collectors;

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
    return new KafkaConsumer<>(this.configuration());
  }

  @Override
  public KafkaProducer<K, X> producer() {
    return new KafkaProducer<>(this.configuration());
  }

  /**
   * @todo #147:30m/DEV Implement via new class.
   * Implement method as `Configuration` class
   * which implement `Scalar<Map<String, Object>>` interface.
   */
  /**
   * It takes all the parameters in the `params` object
   * and returns a map of the parameter names and values.
   *
   * @return A map of the parameters and their values.
   */
  private Map<String, Object> configuration() {
    return this.params
      .all()
      .stream()
      .collect(Collectors.toMap(ParamsAttr::name, ParamsAttr::value));
  }
}
