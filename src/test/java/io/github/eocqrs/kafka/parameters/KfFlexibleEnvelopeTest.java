package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.Params;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link KfFlexibleEnvelope}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
final class KfFlexibleEnvelopeTest {

  @Test
  void createsWithEnvelope() {
    final KfFlexibleEnvelope<String, String> envelope =
      new KfFlexibleEnvelopeTest.Example(
        new KfParams(
          new ClientId("test")
        )
      );
    MatcherAssert.assertThat(
      "Creates with Envelope",
      envelope,
      Matchers.notNullValue()
    );
  }

  private static class Example extends KfFlexibleEnvelope<String, String> {

    protected Example(final Params params) {
      super(params);
    }

    @Override
    public KafkaConsumer<String, String> consumer() {
      return new KafkaConsumer<>(new MapOf<>());
    }

    @Override
    public KafkaProducer<String, String> producer() {
      return new KafkaProducer<>(new MapOf<>());
    }
  }
}
