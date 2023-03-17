package io.github.eocqrs.kafka.consumer;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Consumer;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

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
}