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

package io.github.eocqrs.kafka.data;

import io.github.eocqrs.kafka.Data;
import io.github.eocqrs.kafka.Dataized;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test case for {@link KfDataized}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("deprecation")
final class KfDataizedTest {

  @Test
  void dataizesToInt() {
    final int data = 13491;
    final Dataized<Integer> dataized = new KfDataized<>(data);
    assertThat(dataized.dataize()).isEqualTo(data);
  }

  @Test
  void dataizesToString() {
    final String data = "data";
    final Dataized<String> dataized = new KfDataized<>(data);
    assertThat(dataized.dataize()).isEqualTo(data);
  }

  @Test
  void dataizesToData() {
    final String customer = "customer-1";
    final Data<String> data = new KfData<>(
      "customer-1", "customers", 5
    );
    assertThat(data.dataized().dataize())
      .isEqualTo(customer);
  }
}