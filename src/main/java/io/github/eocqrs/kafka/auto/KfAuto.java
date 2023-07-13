package io.github.eocqrs.kafka.auto;

import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.xml.ConsumerXmlMapParams;
import io.github.eocqrs.kafka.yaml.YamlMapParams;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.Scalar;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public final class KfAuto<K, X>
  implements ConsumerSettings<K, X>, ProducerSettings<K, X> {

  private final Map<Path, SupportedType> consumersParams;

  private final Map<Path, SupportedType> producersParams;

  private final Map<SupportedType, Scalar<Map<String, Object>>> consumers;

  private final Map<SupportedType, Map<String, Object>> producers;

  @SneakyThrows
  public KfAuto(final AutoNames names) {
    this.consumers = Map.of(
      SupportedType.YAML, new YamlMapParams("consumer.yaml"),
      SupportedType.XML, new ConsumerXmlMapParams("consumer.xml")
    );
    this.producers = Map.of(
      SupportedType.YAML, new YamlMapParams("producer.yaml").value()
    );
    this.consumersParams = KfAuto.ejectParams(names, "consumer");
    this.producersParams = KfAuto.ejectParams(names, "producer");
  }

  private static Map<Path, SupportedType> ejectParams(final AutoNames names, final String producer) {
    return names.value()
      .entrySet()
      .stream()
      .filter(pathType -> pathType.getKey().toString().contains(producer))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public KafkaConsumer<K, X> consumer() {
    return this.consumersParams.entrySet()
      .stream()
      .map(
        pathType -> {
          try {
            return new KafkaConsumer<K, X>(
              this.consumers.get(
                pathType.getValue()
              ).value()
            );
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        }
      ).findAny()
      .orElseThrow();
  }

  @Override
  public KafkaProducer<K, X> producer() {
    return null;
  }
}
