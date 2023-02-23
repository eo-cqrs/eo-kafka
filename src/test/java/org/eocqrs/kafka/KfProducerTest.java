package org.eocqrs.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
class KfProducerTest {

  @Test
  void testConstruct() throws FileNotFoundException {
    final Producer<String, String> producer =
      new KfProducer<>(
        new KfProducerSettings<String, String>(
          new XMLDocument(
            new File("src/test/resources/settings.xml")
          )
        ).producer()
      );
    assertThat(producer).isNotNull();
  }

  @Test
  void testSendDoesntThrowException() throws FileNotFoundException {
    final Producer<String, String> producer =
      new KfProducer<>(
        new KfProducerSettings<String, String>(
          new XMLDocument(
            new File("src/test/resources/settings.xml")
          )
        ).producer()
      );
    assertDoesNotThrow(() ->
      producer.send(
        "key-0",
        new KfData<>(
          "test-0",
          "testing",
          1
        )
      )
    );
  }
}