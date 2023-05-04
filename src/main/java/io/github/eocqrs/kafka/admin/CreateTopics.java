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

package io.github.eocqrs.kafka.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;

import java.util.List;

/**
 * Create topics.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.1.3
 */
public final class CreateTopics implements Scalar<KafkaFuture<Void>> {

  /**
   * Admin.
   */
  private final AdminClient admin;
  /**
   * Topics to create.
   */
  private final List<NewTopic> topics;

  /**
   * Ctor.
   *
   * @param admn Kafka Admin
   * @param tpcs Topics to create
   * @see AdminClient
   * @see NewTopic
   */
  public CreateTopics(final AdminClient admn, final NewTopic... tpcs) {
    this.admin = admn;
    this.topics = new ListOf<>(tpcs);
  }

  @Override
  public KafkaFuture<Void> value() throws Exception {
    return this.admin.createTopics(this.topics)
      .all();
  }
}
