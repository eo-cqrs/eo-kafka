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

import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.consumer.KfConsumer;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Kafka Consumer IT Case.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
final class ConsumerTest extends KafkaITCase {

  @Test
  void createsConsumerAndSubscribes() throws IOException {
    try (
      final Consumer<String, String> consumer =
        new KfConsumer<>(
          new KfFlexible<>(
            new KfConsumerParams(
              new KfParams(
                new BootstrapServers(servers),
                new GroupId("1"),
                new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
                new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
              )
            )
          )
        )
    ) {
      Assertions.assertDoesNotThrow(
        () -> consumer.subscribe(
          "TEST-TOPIC"
        )
      );
    }
  }
}

