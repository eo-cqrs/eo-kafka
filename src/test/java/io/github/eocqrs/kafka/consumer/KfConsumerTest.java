package io.github.eocqrs.kafka.consumer;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import org.junit.jupiter.api.Test;
import org.cactoos.list.ListOf;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumer}
 *
 * @since 0.0.0
 */
class KfConsumerTest {

  @Test
  void subscribes() throws FileNotFoundException {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfConsumerSettings<String, String>(
          new XMLDocument(
            new File("src/test/resources/consumer.xml")
          )
        ).consumer()
      );
    assertDoesNotThrow(
      () ->
        consumer.subscribe(
          new ListOf<>("transactions-info")
        )
    );
  }

  @Test
  void constructsConsumer() throws FileNotFoundException {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfConsumerSettings<String, String>(
          new XMLDocument(
            new File(
              "src/test/resources/consumer.xml"
            )
          )
        ).consumer()
      );
    assertThat(consumer).isNotNull();
  }

  @Test
  void constructsConsumerWithSettings() throws FileNotFoundException {
    final ConsumerSettings<String, String> settings =
      new KfConsumerSettings<>(
        new XMLDocument(
          new File(
            "src/test/resources/consumer.xml"
          )
        )
      );
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        settings
      );
    assertThat(consumer).isNotNull();
  }

  @Test
  void constructsConsumerFromXML() throws FileNotFoundException {
    final XML settings = new XMLDocument(
      new File(
        "src/test/resources/consumer.xml"
      )
    );
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        settings
      );
    assertThat(consumer).isNotNull();
  }
}