package io.github.eocqrs.simple;

import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.consumer.KfConsumer;
import io.github.eocqrs.kafka.consumer.KfConsumerSettings;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.KfProducerSettings;
import org.junit.jupiter.api.Test;

final class SimpleTest {

  @Test
  void pollsDataCorrectly() {
    try (
      final Producer<String, String> producer =
        new KfProducer<>(new KfProducerSettings<>("settings.xml"));
      final Consumer<String, String> consumer =
        new KfConsumer<String, String>(new KfConsumerSettings<>())
    ) {

    } catch (final Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

}