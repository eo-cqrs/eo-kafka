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

package io.github.eocqrs.kafka.consumer;

import com.jcabi.xml.XML;
import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Dataized;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import io.github.eocqrs.kafka.data.KfData;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Kafka Consumer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
public final class KfConsumer<K, X> implements Consumer<K, X> {

  /**
   * Origin Kafka Consumer.
   */
  private final KafkaConsumer<K, X> origin;

  /**
   * Ctor.
   *
   * @param orgn origin Kafka Consumer
   */
  public KfConsumer(final KafkaConsumer<K, X> orgn) {
    this.origin = orgn;
  }

  /**
   * Ctor.
   *
   * @param settings Consumer Settings
   * @see ConsumerSettings
   */
  public KfConsumer(final ConsumerSettings<K, X> settings) {
    this(
      settings.consumer()
    );
  }

  /**
   * Ctor.
   *
   * @param settings XML settings
   */
  public KfConsumer(final XML settings) {
    this(
      new KfConsumerSettings<>(
        settings
      )
    );
  }

  @Override
  public void subscribe(final Collection<String> topics) {
    this.origin.subscribe(topics);
  }

  /**
   * @todo #41:30m/DEV Data polling
   * <pre>
   * example:
   * origin.poll(timeout)
   *       .records(topic)
   *       .forEach(...ConsumerRecord) {
   *         ...
   *       };
   * </pre>
   */
  @Override
  public List<Dataized<X>> iterate(final String topic, final Duration timeout) {
    final List<Dataized<X>> iterate = new ArrayList<>(13);
    this.origin.poll(
        timeout
      ).records(topic)
      .forEach(
        data ->
          iterate.add(
            new KfData<>(
              data.value(),
              topic,
              data.partition()
            ).dataized()
          )
      );
    return iterate;
  }

  @Override
  public void close() {
    this.origin.close();
  }
}