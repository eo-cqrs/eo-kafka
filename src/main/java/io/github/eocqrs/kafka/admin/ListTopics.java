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
import org.cactoos.Scalar;

import java.util.List;

/**
 * List all topics.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.1.3
 */
public final class ListTopics implements Scalar<List<String>> {

  /**
   * Admin.
   */
  private final AdminClient admin;

  /**
   * Ctor.
   *
   * @param admn Kafka Admin
   * @see AdminClient
   */
  public ListTopics(final AdminClient admn) {
    this.admin = admn;
  }

  @Override
  public List<String> value() throws Exception {
    return this.admin.listTopics()
      .listings()
      .get()
      .stream()
      .map(listing ->
        "id: %s, name: %s, internal: %s"
          .formatted(
            listing.topicId().toString(),
            listing.name(),
            listing.isInternal()
          )
      ).toList();
  }
}
