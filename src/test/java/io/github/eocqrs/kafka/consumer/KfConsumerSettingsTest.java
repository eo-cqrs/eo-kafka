package io.github.eocqrs.kafka.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ConsumerSettings;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link KfConsumerSettings}
 *
 * @since 0.0.0
 */
class KfConsumerSettingsTest {

  @Test
  void testConsumerConstruction() throws FileNotFoundException {
    final ConsumerSettings<String, String> settings =
      new KfConsumerSettings<>(
        new XMLDocument(
          new File(
            "src/test/resources/consumer.xml"
          )
        )
      );
    final KafkaConsumer<String, String> out = settings.consumer();
    assertThat(out).isNotNull();
    out.close();
  }
}