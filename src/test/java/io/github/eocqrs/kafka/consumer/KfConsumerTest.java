package io.github.eocqrs.kafka.consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Consumer;
import java.io.File;
import java.io.FileNotFoundException;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link KfConsumer}
 *
 * @since 0.0.0
 */
class KfConsumerTest {

  @Test
  void testSubscribe() throws FileNotFoundException {
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

  /**
   * @todo #41:30m/DEV Consumer data polling Test
   */
  @Test
  void testDataized() {
  }
}