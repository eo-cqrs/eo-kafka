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

package io.github.eocqrs.kafka.producer;

import io.github.eocqrs.kafka.Data;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.ProducerSettings;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/*
 * @todo #204:DEV/30min Integration test for `KfCallback`
 *  Create an integration test to test the callback mechanism.
 * */
/**
 * Kafka Producer with callback, decorator for {@link Producer}.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Ivan Ivanchuck (l3r8y@duck.com)
 * @since 0.1.0
 */
public final class KfCallback<K, X> implements Producer<K, X> {

  /**
   * The origin.
   */
  private final KafkaProducer<K, X> origin;

  /**
   * The callback.
   */
  private final Callback callback;

  /**
   * Settings ctor.
   *
   * @param settings The producer settings
   * @param callback The callback
   */
  public KfCallback(
    final ProducerSettings<K, X> settings,
    final Callback callback
  ) {
    this(settings.producer(), callback);
  }

  /**
   * Primary ctor.
   *
   * @param origin The origin producer.
   * @param callback The callback
   */
  public KfCallback(final KafkaProducer<K, X> origin, final Callback callback) {
    this.origin = origin;
    this.callback = callback;
  }

  @Override
  public Future<RecordMetadata> send(final K key, final Data<X> data) {
    return this.origin.send(
      new ProducerRecord<>(
        data.topic(),
        data.partition(),
        key,
        data.dataized().dataize()
      ),
      this.callback
    );
  }

  @Override
  public void close() {
    this.origin.close();
  }
}
