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
import io.github.eocqrs.xfake.InFile;
import io.github.eocqrs.xfake.Synchronized;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
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
import java.util.UUID;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    final Consumer<String, String> consumer =
      new FkConsumer<>(UUID.randomUUID(), mock);
    MatcherAssert.assertThat(
      "Fake consumer creates with mock broker",
      consumer,
      Matchers.notNullValue()
    );
    consumer.close();
  }

  @Test
  void creates() throws IOException {
    final Consumer<String, String> consumer =
      new FkConsumer<>(
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
    final Consumer<String, String> consumer =
      new FkConsumer<>(
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
    final Consumer<String, String> consumer =
      new FkConsumer<>(
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
    final Consumer<String, String> consumer =
      new FkConsumer<>(
        UUID.randomUUID(),
        with
      );
    Assertions.assertDoesNotThrow(() -> consumer.subscribe(topic));
    consumer.close();
  }

  @Test
  void throwsIfNull() {
    final Consumer<String, String> consumer =
      new FkConsumer<>(
        UUID.randomUUID(),
        this.broker
      );
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> consumer.subscribe((String) null)
    );
  }

  @Test
  void createsFakeConsumer() {
    final FkConsumer<String, String> consumer =
      new FkConsumer<>(UUID.randomUUID(), this.broker);
    MatcherAssert.assertThat(consumer, Matchers.is(Matchers.notNullValue()));
    assertThrows(
      UnsupportedOperationException.class,
      () -> consumer.records("123", Duration.ofMillis(100L))
    );
    assertThrows(
      UnsupportedOperationException.class,
      () -> consumer.records("123", Duration.ofMillis(100L))
    );
    assertThrows(
      UnsupportedOperationException.class,
      consumer::unsubscribe
    );
  }

  @Test
  void closesWithoutException() {
    final Consumer<String, String> consumer =
      new FkConsumer<>(UUID.randomUUID(), this.broker);
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
