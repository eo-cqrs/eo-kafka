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

package io.github.eocqrs.kafka.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.github.eocqrs.kafka.Data;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link KfData}
 *
 * @since 0.0.0
 */
class KfDataTest {

  @Test
  void stringDataized() {
    final String origin = "test";
    final Data<String> data = new KfData<>(
      origin, "processes", 3
    );
    assertDoesNotThrow(data::dataized);
    assertThat(data.dataized()).isNotNull();
  }

  @Test
  void topic() {
    final String topic = "tx-log";
    final Data<String> data = new KfData<>(
      "test", topic, 12
    );
    assertThat(data.topic()).isEqualTo(topic);
  }

  @Test
  void partition() {
    final int partition = 1;
    final Data<String> data = new KfData<>(
      "test", "customer-messages", partition
    );
    assertThat(data.partition()).isEqualTo(partition);
  }
}