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

package io.github.eocqrs.kafka.act;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.cactoos.list.ListOf;

import java.util.Collection;

/**
 * Assign Partitions for Kafka Consumer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.5
 */
public final class AssignPartitions implements Action {

  /**
   * Consumer.
   */
  private final KafkaConsumer<?, ?> consumer;
  /**
   * Partitions to assign.
   */
  private final Collection<TopicPartition> partitions;

  /**
   * Ctor.
   *
   * @param cnsmr Kafka Consumer
   * @param prts  Partitions to assign
   */
  public AssignPartitions(
    final KafkaConsumer<?, ?> cnsmr,
    final Collection<TopicPartition> prts
  ) {
    this.consumer = cnsmr;
    this.partitions = prts;
  }

  /**
   * Ctor.
   *
   * @param cnsmr Kafka Consumer
   * @param prts  Partitions to assign.
   */
  public AssignPartitions(
    final KafkaConsumer<?, ?> cnsmr,
    final TopicPartition... prts
  ) {
    this(cnsmr, new ListOf<>(prts));
  }

  @Override
  public void apply() {
    this.consumer.assign(this.partitions);
  }
}
