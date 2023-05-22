/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Aliaksei Bialiauski, EO-CQRS
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

import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.KeySerializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueSerializer;
import io.github.eocqrs.kafka.producer.KfCallback;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Kafka Producer IT Case
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
final class ProducerTest extends KafkaITCase {

  @Test
  void createsProducerAndSendsData() throws IOException {
    try (
      final Producer<String, String> producer =
        new KfProducer<>(
          new KfFlexible<>(
            new KfProducerParams(
              new KfParams(
                new BootstrapServers(servers),
                new KeySerializer("org.apache.kafka.common.serialization.StringSerializer"),
                new ValueSerializer("org.apache.kafka.common.serialization.StringSerializer")
              )
            )
          )
        )
    ) {
      Assertions.assertDoesNotThrow(
        () -> producer.send(
          "fake-key",
          new KfData<>("fake-data", "FAKE-TOPIC", 1)
        )
      );
    }
  }

  @Test
  void createsProducerAndSendsDataWithCallback() throws Exception {
    try (
      final Producer<String, String> producer =
        new KfCallback<>(
          new KfFlexible<>(
            new KfProducerParams(
              new KfParams(
                new BootstrapServers(servers),
                new KeySerializer("org.apache.kafka.common.serialization.StringSerializer"),
                new ValueSerializer("org.apache.kafka.common.serialization.StringSerializer")
              )
            )
          ),
          (recordMetadata, e) ->
            MatcherAssert.assertThat(
              recordMetadata.topic(),
              Matchers.equalTo("TEST-CALLBACK")
            )
        )
    ) {
      producer.send(
        "test-key",
        new KfData<>("test-data", "TEST-CALLBACK", 1)
      );
    }
  }
}
