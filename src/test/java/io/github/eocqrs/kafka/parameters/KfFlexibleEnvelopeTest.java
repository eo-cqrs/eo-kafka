/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2024 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
