/*
 *  Copyright (c) 2022 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
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

import com.jcabi.xml.XML;
import io.github.eocqrs.kafka.Data;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.producer.settings.KfProducerSettings;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Kafka Producer.
 *
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
    this(
      settings.producer()
    );
  }

  /**
   * Ctor.
   *
   * @param settings XML settings
   */
  public KfProducer(final XML settings) {
    this(
      new KfProducerSettings<K, X>(
        settings
      ).producer()
    );
  }

  @Override
  public void send(final K key, final Data<X> data) {
    this.origin.send(
      new ProducerRecord<>(
        data.topic(),
        data.partition(),
        key,
        data.dataized()
          .dataize()
      )
    );
  }

  @Override
  public void close() {
    this.origin.close();
  }
}