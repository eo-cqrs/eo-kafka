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

package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.ProducerSettings;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * KfFlexibleSettings allow you to add custom settings.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
public final class KfFlexible<K, X>
  implements ConsumerSettings<K, X>, ProducerSettings<K, X> {

  /**
   * The params.
   */
  private final Params params;

  @Override
  public KafkaConsumer<K, X> consumer() {
    return new KafkaConsumer<>(new MapParams(this.params).value());
  }

  @Override
  public KafkaProducer<K, X> producer() {
    return new KafkaProducer<>(new MapParams(this.params).value());
  }
}
