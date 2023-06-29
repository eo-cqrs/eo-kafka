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

package io.github.eocqrs.kafka.fake;

import com.jcabi.log.Logger;
import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.data.Tkv;
import io.github.eocqrs.kafka.data.WithPartition;
import io.github.eocqrs.xfake.InFile;
import io.github.eocqrs.xfake.Synchronized;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Test case for {@link FkConsumer}.
 *
 * @author Ivan Ivanchuck (l3r8y@duck.com)
 * @since 0.0.3
 */
@ExtendWith(MockitoExtension.class)
final class FkConsumerTest {

  /**
   * Broker.
   */
  private FkBroker broker;

  @BeforeEach
  void setUp() throws Exception {
    this.broker = new InXml(
      new Synchronized(
        new InFile(
          "consumer-test", "<broker/>"
        )
      )
    );
  }

  @Test
  void createsWithMockBroker(@Mock final FkBroker mock) throws IOException {
    final Consumer<Object, String> consumer =
      new FkConsumer(UUID.randomUUID(), mock);
    MatcherAssert.assertThat(
      "Fake consumer creates with mock broker",
      consumer,
      Matchers.notNullValue()
    );
    consumer.close();
  }

  @Test
  void creates() throws IOException {
    final Consumer<Object, String> consumer =
      new FkConsumer(
        UUID.randomUUID(),
        this.broker
      );
    MatcherAssert.assertThat(
      "Fake consumer creates",
      consumer,
      Matchers.notNullValue()
    );
    consumer.close();
  }

  @Test
  void subscribesToTopics() throws Exception {
    final String topic = "1.test";
    final UUID uuid = UUID.fromString("4b9d33f3-662f-41cf-a500-6169779e802a");
    final FkBroker with = this.broker
      .with(new TopicDirs(topic).value());
    final Consumer<Object, String> consumer =
      new FkConsumer(
        uuid,
        with
      );
    consumer.subscribe(new ListOf<>(topic));
    MatcherAssert.assertThat(
      "topic subscriptions in right format",
      with.data(
        "broker/subs/sub[topic = '%s']/topic/text()"
          .formatted(
            topic
          )
      ),
      Matchers.contains(topic)
    );
    MatcherAssert.assertThat(
      "Consumer ID in right format",
      with.data(
        "broker/subs/sub[consumer = '%s']/consumer/text()"
          .formatted(
            uuid.toString()
          )
      ),
      Matchers.contains(uuid.toString())
    );
    consumer.close();
  }

  @Test
  void subscribesWithRebalanceListener() throws Exception {
    final String topic = "listener.test";
    final String rebalance = "rebalance";
    final UUID uuid = UUID.fromString("e343a512-9d02-40e8-92b6-1538014d3975");
    final FkBroker with = this.broker
      .with(new TopicDirs(topic).value());
    final Consumer<Object, String> consumer =
      new FkConsumer(
        uuid,
        with
      );
    final ConsumerRebalanceListener listener = new ConsumerRebalanceListener() {
      @Override
      public void onPartitionsRevoked(final Collection<TopicPartition> collection) {
      }

      @Override
      public void onPartitionsAssigned(final Collection<TopicPartition> collection) {
      }

      @Override
      public String toString() {
        return rebalance;
      }
    };
    consumer.subscribe(listener, topic);
    MatcherAssert.assertThat(
      "topic subscriptions in right format",
      with.data(
        "broker/subs/sub[topic = '%s']/topic/text()"
          .formatted(
            topic
          )
      ),
      Matchers.contains(topic)
    );
    MatcherAssert.assertThat(
      "Consumer ID in right format",
      with.data(
        "broker/subs/sub[consumer = '%s']/consumer/text()"
          .formatted(
            uuid.toString()
          )
      ),
      Matchers.contains(uuid.toString())
    );
    MatcherAssert.assertThat(
      "Consumer Rebalance Listener in right format",
      with.data(
        "broker/subs/sub[listener = '%s']/listener/text()"
          .formatted(listener.toString())
      ),
      Matchers.contains(rebalance)
    );
    consumer.close();
  }

  @Test
  void subscribesWithVarArgs() throws Exception {
    final String topic = "varargs.test";
    final FkBroker with = this.broker
      .with(new TopicDirs(topic).value());
    final Consumer<Object, String> consumer =
      new FkConsumer(
        UUID.randomUUID(),
        with
      );
    Assertions.assertDoesNotThrow(() -> consumer.subscribe(topic));
    consumer.close();
  }

  @Test
  void throwsIfNull() throws IOException {
    final Consumer<Object, String> consumer =
      new FkConsumer(
        UUID.randomUUID(),
        this.broker
      );
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> consumer.subscribe((String) null)
    );
    consumer.close();
  }

  @Test
  void throwsIfNullWithListener() {
    final Consumer<Object, String> consumer =
      new FkConsumer(
        UUID.randomUUID(),
        this.broker
      );
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> consumer.subscribe(new ConsumerRebalanceListener() {
        @Override
        public void onPartitionsRevoked(final Collection<TopicPartition> collection) {
        }

        @Override
        public void onPartitionsAssigned(final Collection<TopicPartition> collection) {
        }
      }, (String) null)
    );
  }

  @Test
  void unsubscribes() throws Exception {
    final String topic = "unsubscribe.test";
    final UUID uuid = UUID.randomUUID();
    final Consumer<Object, String> consumer =
      new FkConsumer(
        uuid,
        this.broker
      );
    consumer.subscribe(topic);
    consumer.unsubscribe();
    MatcherAssert.assertThat(
      "Consumer ID in subscription is not present",
      this.broker.data(
          "broker/subs/sub[consumer = '%s']/consumer/text()"
            .formatted(
              uuid
            )
        )
        .isEmpty(),
      Matchers.equalTo(true)
    );
    MatcherAssert.assertThat(
      "Topic in subscription is not present",
      this.broker.data(
          "broker/subs/sub[topic = '%s']/topic/text()"
            .formatted(
              topic
            )
        )
        .isEmpty(),
      Matchers.equalTo(true)
    );
    consumer.close();
  }

  @Test
  void unsubscribesWithSecondConsumerExisting() throws Exception {
    final String topic = "unsubscribes.with.second.consumer.existing";
    final UUID firstID = UUID.fromString("f3000fb7-b9fb-42d0-8210-f09a58c44a1f");
    final UUID secondID = UUID.fromString("69a4cd5a-afdb-456c-9ade-658569f52d7b");
    final Consumer<Object, String> first =
      new FkConsumer(
        firstID,
        this.broker
      );
    final Consumer<Object, String> second =
      new FkConsumer(
        secondID,
        this.broker
      );
    first.subscribe(topic);
    second.subscribe(topic);
    first.unsubscribe();
    MatcherAssert.assertThat(
      "No such subscription with first Consumer ID",
      this.broker.data(
        "broker/subs/sub[topic = '%s' and consumer = '%s']/topic/text()"
          .formatted(
            topic,
            firstID
          )
      ).isEmpty(),
      Matchers.equalTo(true)
    );
    MatcherAssert.assertThat(
      "Topic with subscription exists with second Consumer ID",
      this.broker.data(
          "broker/subs/sub[topic = '%s' and consumer = '%s']/topic/text()"
            .formatted(
              topic,
              secondID
            )
        )
        .isEmpty(),
      Matchers.equalTo(false)
    );
    first.close();
    second.close();
  }

  @Test
  void pollsRecordsWithoutException() throws Exception {
    final Consumer<Object, String> consumer =
      new FkConsumer(
        UUID.randomUUID(),
        this.broker
      );
    Assertions.assertDoesNotThrow(
      () ->
        consumer.records("test", Duration.ofSeconds(5L))
    );
    consumer.close();
  }

  @Test
  void pollsRecords() throws Exception {
    final String topic = "test";
    final Consumer<Object, String> consumer =
      new FkConsumer(UUID.randomUUID(),
        this.broker
          .with(new TopicDirs(topic).value())
      );
    final Producer<String, String> producer =
      new FkProducer<>(UUID.randomUUID(), this.broker);
    producer.send(
      new WithPartition<>(
        0,
        new Tkv<>(
          topic,
          "test1",
          "test1"
        )
      )
    );
    producer.send(
      new WithPartition<>(
        0,
        new Tkv<>(
          topic,
          "test2",
          "test2"
        )
      )
    );
    producer.send(
      new WithPartition<>(
        0,
        new Tkv<>(
          topic,
          "test3",
          "test3"
        )
      )
    );
    final ConsumerRecords<Object, String> first =
      consumer.records(topic, Duration.ofSeconds(1L));
    final ConsumerRecords<Object, String> second =
      consumer.records(topic, Duration.ofSeconds(1L));
    final List<String> datasets = new ListOf<>();
    first.forEach(rec -> datasets.add(rec.value()));
    MatcherAssert.assertThat(
      "First datasets in right format",
      datasets,
      Matchers.contains("test1", "test2", "test3")
    );
    datasets.clear();
    second.forEach(rec -> datasets.add(rec.value()));
    MatcherAssert.assertThat(
      "Second datasets are empty",
      datasets.isEmpty(),
      Matchers.equalTo(true)
    );
    producer.close();
    consumer.close();
  }

  @Test
  void closesWithoutException() {
    final Consumer<Object, String> consumer =
      new FkConsumer(UUID.randomUUID(), this.broker);
    Assertions.assertDoesNotThrow(consumer::close);
  }

  @Test
  void logsWithInfo() {
    MatcherAssert.assertThat(
      "Logging is enabled at level info",
      Logger.isEnabled(Level.INFO, FkConsumer.class),
      Matchers.is(true)
    );
  }
}
