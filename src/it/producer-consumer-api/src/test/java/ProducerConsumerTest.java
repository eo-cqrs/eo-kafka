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
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.consumer.KfConsumer;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.kafka.parameters.AutoOffsetReset;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.ClientId;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KeySerializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import io.github.eocqrs.kafka.parameters.ValueSerializer;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.rnorth.ducttape.unreliables.Unreliables;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Communication between a Producer and Consumer IT Case.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
final class ProducerConsumerTest extends KafkaITCase {

  @Test
  void createsProducerAndSendsMessage() throws Exception {
    final String topic = "TEST-TOPIC";
    final String key = "eo-kafka";
    final String value = "rulezzz";
    final Producer<String, String> producer =
      new KfProducer<>(
        new KfFlexible<>(
          new KfProducerParams(
            new KfParams(
              new BootstrapServers(servers),
              new ClientId(UUID.randomUUID().toString()),
              new KeySerializer(StringSerializer.class.getName()),
              new ValueSerializer(StringSerializer.class.getName())
            )
          )
        )
      );
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfFlexible<>(
          new KfConsumerParams(
            new KfParams(
              new BootstrapServers(servers),
              new GroupId("it-" + UUID.randomUUID()),
              new AutoOffsetReset("earliest"),
              new KeyDeserializer(StringDeserializer.class.getName()),
              new ValueDeserializer(StringDeserializer.class.getName())
            )
          )
        )
      );
    producer.send(key, new KfData<>(value, topic, 0));
    Unreliables.retryUntilTrue(
      10,
      TimeUnit.SECONDS,
      () -> {
        final ConsumerRecords<String, String> records =
          consumer.records(topic, Duration.ofMillis(100L));
        if (records.isEmpty()) {
          return false;
        }
        assertThat(records)
          .hasSize(1)
          .extracting(ConsumerRecord::topic, ConsumerRecord::key, ConsumerRecord::value)
          .containsExactly(tuple(topic, key, value));
        return true;
      }
    );
    consumer.unsubscribe();
  }
}
