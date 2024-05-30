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

package io.github.eocqrs.kafka.fake;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link FkRecords}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
final class FkRecordsTest {

  @Test
  void readsRecordsInRightFormat() throws Exception {
    final String topic = "test";
    final String value = "test";
    new FkRecords(topic, new ListOf<>(value))
      .value()
      .forEach(rec ->
        MatcherAssert.assertThat(
          "Values %s should contain the record %s"
            .formatted(new ListOf<>(value), rec),
          rec.value(),
          Matchers.equalTo(value)
        ));
  }

  @Test
  void readsCountInRightFormat() throws Exception {
    final String topic = "test";
    final ConsumerRecords<Object, String> value = new FkRecords(
      topic,
      new ListOf<>(
        "first",
        "second"
      )
    ).value();
    MatcherAssert.assertThat(
      "Records %s size in equals 2"
        .formatted(value),
      value.count(),
      Matchers.equalTo(2)
    );
  }
}
