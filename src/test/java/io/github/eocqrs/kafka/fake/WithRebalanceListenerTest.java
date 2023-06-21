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

package io.github.eocqrs.kafka.fake;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

/**
 * Test case for {@link WithRebalanceListener}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
final class WithRebalanceListenerTest {

  @Test
  void dirsInRightFormat() throws Exception {
    final UUID id = UUID.fromString("a6d0094c-d393-4d63-bc4c-20b33b38bc8f");
    final String rebalance = "rebalance";
    final String directives =
      "XPATH \"broker/subs\";ADD \"sub\";ADDIF \"topic\";SET \"test\";UP;ADDIF "
        + "\"consumer\";SET \"a6d0094c-d393-4d63-bc4c-20b33b38bc8f\";"
        + "\n7:UP;ADDIF \"listener\";SET \"rebalance\";";
    MatcherAssert.assertThat(
      "Directives in the right format",
      new WithRebalanceListener(new SubscribeDirs("test", id),
        new ConsumerRebalanceListener() {
          @Override
          public void onPartitionsRevoked(final Collection<TopicPartition> collection) {
          }

          @Override
          public void onPartitionsAssigned(final Collection<TopicPartition> collection) {
          }

          @Override
          public String toString() {
            return rebalance;
          }
        })
        .value()
        .toString(),
      Matchers.equalTo(
        directives
      )
    );
  }
}
