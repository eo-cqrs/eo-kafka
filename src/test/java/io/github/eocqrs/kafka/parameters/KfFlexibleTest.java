package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfFlexible}.
 *
 * @author Ivan Ivanchuck (l3r8y@duck.com)
 * @since 0.0.2
 */
final class KfFlexibleTest {

  @Test
  void createsConsumerWithCustomSettings() {
    try (
      final KafkaConsumer<Object, Object> consumer =
        new KfFlexible<>(
          new KfConsumerParams(
            new KfParams(
              new BootstrapServers("localhost:9092"),
              new GroupId("1"),
              new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
              new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
              new KfFlexibleTest.HeartbeatIntervalMs("4234")
            )
          )
        ).consumer()
      ) {
      Assertions.assertThat(consumer).isNotNull();
      assertDoesNotThrow(consumer::subscription);
    }
  }

  private static final class HeartbeatIntervalMs extends KfAttrEnvelope {
    HeartbeatIntervalMs(final String value) {
      super(value, "heartbeat.interval.ms");
    }
  }
}
