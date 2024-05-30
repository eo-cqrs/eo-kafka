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
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.kafka.Message;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Fake Kafka Producer.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
@SuppressWarnings("deprecation")
public final class FkProducer<K, X> implements Producer<K, X> {

  /**
   * Offset.
   */
  private static final long OFFSET = 0L;
  /**
   * Batch Index.
   */
  private static final int BATCH_INDEX = 0;
  /**
   * Timestamp.
   */
  private static final long TIMESTAMP = 0L;
  /**
   * Client id.
   */
  private final UUID id;
  /**
   * Broker.
   */
  private final FkBroker broker;

  /**
   * Ctor.
   *
   * @param client Client UUID id
   * @param brkr   Broker
   */
  public FkProducer(final UUID client, final FkBroker brkr) {
    this.id = client;
    this.broker = brkr;
  }

  @Override
  public Future<RecordMetadata> send(
    final Message<K, X> message
  ) throws Exception {
    final ProducerRecord<K, X> record = message.value();
    new ThrowsOnFalse(
      new TopicExists(record.topic(), this.broker),
      "topic %s does not exists!"
        .formatted(
          record.topic()
        )
    ).value();
    this.broker.with(
      new DatasetDirs<>(record.key(),
        new KfData<>(
          record.value(),
          record.topic(),
          record.partition()
        )
      ).value()
    );
    final RecordMetadata metadata = new RecordMetadata(
      new TopicPartition(
        record.topic(),
        record.partition()
      ),
      OFFSET,
      BATCH_INDEX,
      TIMESTAMP,
      record.key().toString().getBytes().length,
      record.value().toString().getBytes().length
    );
    return new FkMetadataTask(
      metadata
    );
  }

  @Override
  public void close() {
    Logger.info(
      this, "Producer %s closed at %s"
        .formatted(
          this.id,
          LocalDateTime.now(
            Clock.systemUTC()
          )
        )
    );
  }
}
