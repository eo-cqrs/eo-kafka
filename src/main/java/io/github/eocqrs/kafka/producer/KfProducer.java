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

package io.github.eocqrs.kafka.producer;

import io.github.eocqrs.kafka.Message;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.ProducerSettings;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

/**
 * Kafka Producer.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
public final class KfProducer<K, X> implements Producer<K, X> {

  /**
   * Origin Kafka Producer.
   */
  private final KafkaProducer<K, X> origin;

  /**
   * Ctor.
   *
   * @param orgn origin Kafka Producer
   * @see KafkaProducer
   */
  public KfProducer(final KafkaProducer<K, X> orgn) {
    this.origin = orgn;
  }

  /**
   * Ctor.
   *
   * @param settings Producer Settings
   * @see ProducerSettings
   */
  public KfProducer(final ProducerSettings<K, X> settings) {
    this(settings.producer());
  }

  @Override
  public Future<RecordMetadata> send(final Message<K, X> msg)
    throws Exception {
    return this.origin.send(msg.value());
  }

  @Override
  public void close() {
    this.origin.close();
  }
}
