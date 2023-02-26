package org.eocqrs.kafka.consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import org.eocqrs.kafka.ConsumerSettings;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link KfConsumerSettings}
 *
 * @since 0.0.0
 */
class KfConsumerSettingsTest {

  /**
   * @todo #25:20m/DEV Consumer construction Test
   */
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
    assertThrows(
      UnsupportedOperationException.class,
      () -> settings.consumer()
    );
  }
}