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

package io.github.eocqrs.kafka.fake;

import com.jcabi.log.Logger;
import io.github.eocqrs.kafka.Message;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.data.Tkv;
import io.github.eocqrs.kafka.data.WithPartition;
import io.github.eocqrs.xfake.InFile;
import io.github.eocqrs.xfake.Synchronized;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 * Test case for {@link FkProducer}.
 *
 * @author Aliaksei Bialiauski (abialiaski.dev@gmail.com)
 * @since 0.1.3
 */
@ExtendWith(MockitoExtension.class)
final class FkProducerTest {

  /**
   * Broker.
   */
  private FkBroker broker;

  @BeforeEach
  void setUp() throws Exception {
    this.broker = new InXml(
      new Synchronized(
        new InFile(
          "producer-tests", "<broker/>"
        )
      )
    );
  }

  @Test
  void createsFakeProducerWithMockBroker(@Mock final FkBroker mock)
    throws IOException {
    final Producer<String, String> producer =
      new FkProducer<>(UUID.randomUUID(), mock);
    MatcherAssert.assertThat(
      "Fake producer %s created with mock broker %s"
        .formatted(producer, mock),
      producer,
      Matchers.notNullValue()
    );
    producer.close();
  }

  @Test
  void createsFakeProducer() throws IOException {
    final Producer<String, String> producer =
      new FkProducer<>(UUID.randomUUID(), this.broker);
    MatcherAssert.assertThat(
      "Fake producer created %s"
        .formatted(producer),
      producer,
      Matchers.notNullValue()
    );
    producer.close();
  }

  @Test
  void closesWithoutException() {
    final Producer<String, String> producer =
      new FkProducer<>(UUID.randomUUID(), this.broker);
    Assertions.assertDoesNotThrow(producer::close);
  }

  @Test
  void logsWithLevelInfo() {
    MatcherAssert.assertThat(
      "Logging is enabled at level info",
      Logger.isEnabled(Level.INFO, FkProducer.class),
      Matchers.is(true)
    );
  }

  @Test
  void sendsMessageWithoutTopicExistence() throws Exception {
    final Producer<String, String> producer =
      new FkProducer<>(UUID.randomUUID(), this.broker);
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () ->
        producer.send(
          new WithPartition<>(
            0,
            new Tkv<>(
              "doesn.not.exist",
              "test-key",
              "data"
            )
          )
        ),
      () ->
        "Throws %s when topic not exist"
          .formatted(IllegalArgumentException.class)
    );
    producer.close();
  }

  @Test
  void sendsMessage() throws Exception {
    final String topic = "test.fake";
    final String data = "data";
    final int partition = 0;
    final FkBroker after = this.broker
      .with(new TopicDirs(topic).value());
    final Producer<String, String> producer =
      new FkProducer<>(
        UUID.randomUUID(),
        after
      );
    final Message<String, String> tkv = new Tkv<>(
      topic,
      "test-key",
      data
    );
    producer.send(
      new WithPartition<>(
        partition,
        tkv
      )
    );
    final Collection<String> sent = after.data(
      "broker/topics/topic[name = '%s']/datasets/dataset[value = '%s']/text()"
        .formatted(
          topic,
          data
        )
    );
    MatcherAssert.assertThat(
      "%s data is not blank after producer sent %s"
        .formatted(sent, tkv.value()),
      sent.isEmpty(),
      Matchers.equalTo(false)
    );
    producer.close();
  }

  @Test
  void readsMetadataInRightFormat() throws Exception {
    final String topic = "metadata.test";
    final int partition = 0;
    final Producer<String, String> producer =
      new FkProducer<>(
        UUID.randomUUID(),
        this.broker
          .with(new TopicDirs(topic).value())
      );
    final Future<RecordMetadata> future = producer.send(
      new WithPartition<>(
        partition,
        new Tkv<>(
          topic,
          "test-key",
          "test-data"
        )
      )
    );
    final RecordMetadata metadata = future.get();
    MatcherAssert.assertThat(
      "Metadata topic in right format",
      metadata.topic(),
      Matchers.equalTo(topic)
    );
    MatcherAssert.assertThat(
      "Metadata partition in right format",
      metadata.partition(),
      Matchers.equalTo(partition)
    );
    producer.close();
  }
}
