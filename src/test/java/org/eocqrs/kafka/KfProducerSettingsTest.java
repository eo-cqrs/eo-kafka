package org.eocqrs.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
class KfProducerSettingsTest {

  @Test
  void testProducerConstruction() throws FileNotFoundException {
    final ProducerSettings<String, String> settings =
      new KfProducerSettings<>(
        new XMLDocument(
          new File("src/test/resources/settings.xml")
        )
      );
    final KafkaProducer<String, String> out = settings.producer();
    assertThat(out).isNotNull();
  }
}