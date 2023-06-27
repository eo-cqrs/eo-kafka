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

package io.github.eocqrs.kafka.fake;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fake Records.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
public final class FkRecords implements
  Scalar<ConsumerRecords<Object, String>> {

  /*
   * @todo #303:45m/DEV multi partitioning is not supported
   */
  /**
   * Default Partition.
   */
  private static final int DEFAULT_PARTITION = 0;
  /**
   * Zero Offset.
   */
  private static final long ZERO_OFFSET = 0L;
  /**
   * Records related to a topic.
   */
  private final String topic;
  /**
   * Dataset to transform.
   */
  private final Collection<String> datasets;

  /**
   * Ctor.
   *
   * @param tpc  Topic
   * @param dtst Dataset to transform
   */
  public FkRecords(
    final String tpc,
    final Collection<String> dtst
  ) {
    this.topic = tpc;
    this.datasets = dtst;
  }

  @Override
  public ConsumerRecords<Object, String> value() throws Exception {
    final Map<TopicPartition, List<ConsumerRecord<Object, String>>> part
      = new HashMap<>();
    final List<ConsumerRecord<Object, String>> recs = new ListOf<>();
    this.datasets.forEach(
      dataset -> recs.add(
        new ConsumerRecord<>(
          this.topic,
          DEFAULT_PARTITION,
          ZERO_OFFSET,
          null,
          dataset
        )
      )
    );
    part.put(new TopicPartition(this.topic, DEFAULT_PARTITION), recs);
    return new ConsumerRecords<>(part);
  }
}
