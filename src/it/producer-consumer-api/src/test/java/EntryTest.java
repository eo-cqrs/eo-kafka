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
import io.github.eocqrs.kafka.producer.KfCallback;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Entry test cases.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
@Deprecated(forRemoval = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class EntryTest {

  private static final KafkaContainer KAFKA = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.3.0")
  )
    .withEnv("KAFKA_CREATE_TOPICS", "TEST-TOPIC")
    .withEnv("auto.create.topics.enable", "true")
    .withReuse(true)
    .withLogConsumer(
      new Slf4jLogConsumer(
        LoggerFactory.getLogger(
          "testcontainers.kafka"
        )
      )
    )
    .withEmbeddedZookeeper();

  private static String servers;

  @BeforeAll
  static void setup() {
    EntryTest.KAFKA.start();
    EntryTest.servers =
      EntryTest.KAFKA.getBootstrapServers().replace("PLAINTEXT://", "");
    KAFKA.setEnv(
      new ListOf<>(
        "KAFKA_ADVERTISED_LISTENERS="
          + EntryTest.KAFKA.getBootstrapServers(),
        "KAFKA_LISTENERS=LISTENER_PUBLIC://" +
          EntryTest.KAFKA.getContainerName() +
          ":29092,LISTENER_INTERNAL://" +
          EntryTest.KAFKA.getBootstrapServers(),
        "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=LISTENER_PUBLIC:PLAINTEXT,LISTENER_INTERNAL:PLAINTEXT",
        "KAFKA_INTER_BROKER_LISTENER_NAME=LISTENER_PUBLIC"
      )
    );
  }

  @Disabled
  @Test
  @Order(1)
  void runsKafka() {
    MatcherAssert.assertThat(
      "Container runs",
      EntryTest.KAFKA.isRunning(),
      Matchers.is(true)
    );
  }

  @Disabled
  @Test
  @Order(2)
  void createsConsumerAndSubscribes() throws IOException {
    try (
      final Consumer<String, String> consumer =
        new KfConsumer<>(
          new KfFlexible<>(
            new KfConsumerParams(
              new KfParams(
                new BootstrapServers(EntryTest.servers),
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

  @Disabled
  @Test
  @Order(3)
  void createsProducerAndSendsData() throws IOException {
    try (
      final Producer<String, String> producer =
        new KfProducer<>(
          new KfFlexible<>(
            new KfProducerParams(
              new KfParams(
                new BootstrapServers(EntryTest.servers),
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

  @Disabled
  @Test
  @Order(5)
  void createsProducerAndSendsMessage() throws Exception {
    final Producer<String, String> producer = new KfProducer<>(
      new KfFlexible<>(
        new KfProducerParams(
          new KfParams(
            new BootstrapServers(EntryTest.servers),
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
              new BootstrapServers(EntryTest.servers),
              new GroupId("it-" + UUID.randomUUID()),
              new AutoOffsetReset("earliest"),
              new KeyDeserializer(StringDeserializer.class.getName()),
              new ValueDeserializer(StringDeserializer.class.getName())
            )
          )
        )
      );
    producer.send("testcontainers", new KfData<>("rulezzz", "TEST-TOPIC", 0));
    Unreliables.retryUntilTrue(
      10,
      TimeUnit.SECONDS,
      () -> {
        final ConsumerRecords<String, String> records =
          consumer.records("TEST-TOPIC", Duration.ofMillis(100L));
        if (records.isEmpty()) {
          return false;
        }
        assertThat(records)
          .hasSize(1)
          .extracting(ConsumerRecord::topic, ConsumerRecord::key, ConsumerRecord::value)
          .containsExactly(tuple("TEST-TOPIC", "testcontainers", "rulezzz"));
        return true;
      }
    );
    consumer.unsubscribe();
  }

  @Disabled
  @Test
  @Order(4)
  void createsProducerWithCallback() throws Exception {
    try (
      final Producer<String, String> producer =
        new KfCallback<>(
          new KfFlexible<>(
            new KfProducerParams(
              new KfParams(
                new BootstrapServers(EntryTest.servers),
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
