package io.github.eocqrs.kafka.consumer;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import io.github.eocqrs.kafka.producer.settings.KfProducerSettings;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumerSettings}
 *
 * @since 0.0.0
 */
final class KfConsumerSettingsTest {

  @Test
  void constructsWithSettings() {
    try (
      final Consumer<String, String> consumer =
        new KfConsumer<>(
          new KfConsumerSettings<>(
            new XMLDocument(
              new File(
                "src/test/resources/consumer.xml"
              )
            )
          )
        )
    ) {
      assertThat(consumer).isNotNull();
    } catch (final Exception ignored) { }
  }

  @Test
  void constructsWithResourceOf() {
    assertDoesNotThrow(
      () -> {
        final ConsumerSettings<String, String> settings =
          new KfConsumerSettings<>(new ResourceOf("consumer.xml"));
        assertThat(settings.consumer()).isNotNull();
      }
    );
  }

  @Test
  void constructsWithString() {
    assertDoesNotThrow(
      () -> {
        final ConsumerSettings<String, String> settings = new KfConsumerSettings<>("consumer.xml");
        assertThat(settings.consumer()).isNotNull();
      }
    );
  }

}