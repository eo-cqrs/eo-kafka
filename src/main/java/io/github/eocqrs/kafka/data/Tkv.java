/*
 *  Copyright (c) 2023 Aliaksei Bialiauski, EO-CQRS
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

package io.github.eocqrs.kafka.data;

import io.github.eocqrs.kafka.Message;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Topic, Key, Value.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.6
 */
public final class Tkv<K, X> implements Message<K, X> {

  /**
   * Topic.
   */
  private final String topic;
  /**
   * Key.
   */
  private final K key;
  /**
   * Value.
   */
  private final X value;

  /**
   * Ctor.
   *
   * @param tpc Topic
   * @param key Key
   * @param val Value
   */
  public Tkv(
    final String tpc,
    final K key,
    final X val
  ) {
    this.topic = tpc;
    this.key = key;
    this.value = val;
  }

  @Override
  public ProducerRecord<K, X> value() throws Exception {
    return new ProducerRecord<>(
      this.topic,
      this.key,
      this.value
    );
  }
}
