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
import io.github.eocqrs.kafka.Dataized;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.consumer.KfConsumer;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.ClientId;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KeySerializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.Retries;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import io.github.eocqrs.kafka.parameters.ValueSerializer;
import io.github.eocqrs.kafka.producer.KfCallback;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
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
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

/**
 * @todo #81 Tests to produce-consume data.
 * Write a test which will be check how consumer
 * reads data from producer.
 */

/**
 * @todo #236:30m/DEV Enable tests
 */

/**
 * Entry test cases.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class EntryTest {

  private static final KafkaContainer KAFKA = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.3.0")
  )
    .withEnv("auto.create.topics.enable", "true")
    .withEnv("KAFKA_CREATE_TOPICS", "TEST-TOPIC")
    .withReuse(true)
    .withLogConsumer(
      new Slf4jLogConsumer(
        LoggerFactory.getLogger(
          "testcontainers.kafka"
        )
      )
    )
    .withExternalZookeeper("localhost:2181");

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
      Assertions.assertDoesNotThrow(() -> consumer.subscribe(new ListOf<>("fake")));
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
          new KfData<>("fake-data", "TEST-TOPIC", 1)
        )
      );
    }
  }

  @Test
  @Order(5)
  void createsProducerAndSendsMessage() throws Exception {
    final AdminClient admin = AdminClient.create(
      ImmutableMap.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, EntryTest.servers)
    );
    final KafkaProducer<String, String> producer = new KafkaProducer<>(
      ImmutableMap.of(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        EntryTest.servers,
        ProducerConfig.CLIENT_ID_CONFIG,
        UUID.randomUUID().toString()
      ),
      new StringSerializer(),
      new StringSerializer()
    );
    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
      ImmutableMap.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        EntryTest.servers,
        ConsumerConfig.GROUP_ID_CONFIG,
        "tc-" + UUID.randomUUID(),
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        "earliest"
      ),
      new StringDeserializer(),
      new StringDeserializer()
    );
    final Collection<NewTopic> topics =
      Collections.singletonList(
        new NewTopic("TEST-TOPIC", 1, (short) 1)
      );
    admin.createTopics(topics)
      .all().get(30L, TimeUnit.SECONDS);
    consumer.subscribe(Collections.singletonList("TEST-TOPIC"));
    producer.send(new ProducerRecord<>("TEST-TOPIC", "testcontainers", "rulezzz")).get();
    Unreliables.retryUntilTrue(
      10,
      TimeUnit.SECONDS,
      () -> {
        final ConsumerRecords<String, String> records =
          consumer.poll(Duration.ofMillis(100L));
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
  void createsProducerWithCallback() throws IOException {
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
              Matchers.equalTo("TEST-TOPIC")
            )
        )
    ) {
      producer.send(
        "test-key",
        new KfData<>("test-data", "TEST-TOPIC", 1)
      );
    }
  }
}
