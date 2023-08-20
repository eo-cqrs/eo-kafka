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
import org.cactoos.scalar.Sticky;

/**
 * Message with specified partition.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.6
 */
public final class WithPartition<K, X> implements Message<K, X> {

  /**
   * Partition.
   */
  private final int partition;
  /**
   * Message.
   */
  private final Sticky<ProducerRecord<K, X>> message;

  /**
   * Ctor.
   *
   * @param prtn Partition
   * @param msg  Message
   */
  public WithPartition(
    final int prtn,
    final Message<K, X> msg
  ) {
    this.partition = prtn;
    this.message = new Sticky<>(msg);
  }

  @Override
  public ProducerRecord<K, X> value() throws Exception {
    return new ProducerRecord<>(
      this.message.value().topic(),
      this.partition,
      this.message.value().key(),
      this.message.value().value()
    );
  }
}
