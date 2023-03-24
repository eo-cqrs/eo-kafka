package io.github.eocqrs.kafka.consumer;

import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import io.github.eocqrs.kafka.xml.KfXmlFlexible;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumer}
 *
 * @since 0.0.0
 */
final class KfConsumerTest {

  @Test
  void subscribes() throws Exception {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfXmlFlexible<String, String>(
          "consumer.xml"
        ).consumer()
      );
    assertDoesNotThrow(
      () ->
        consumer.subscribe(
          new ListOf<>("transactions-info")
        )
    );
    assertDoesNotThrow(
      consumer::close
    );
  }

  @Test
  void constructsConsumerWithXML() throws Exception {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfXmlFlexible<String, String>("consumer.xml")
          .consumer()
      );
    assertThat(consumer).isNotNull();
  }

  @Test
  void constructsConsumerWithParams() {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfFlexible<>(
          new KfConsumerParams(
            new KfParams(
              new BootstrapServers("localhost:9092"),
              new GroupId("1"),
              new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
              new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
            )
          )
        )
      );
    assertThat(consumer).isNotNull();
  }
}
