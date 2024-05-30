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

package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ParamsAttr;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ValueDeserializer}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
final class ValueDeserializerTest {

  /**
   * Under test.
   */
  private ParamsAttr key;

  @BeforeEach
  void setUp() {
    this.key = new ValueDeserializer("vd");
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.key.name(),
      Matchers.equalTo("value.deserializer")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.key.asXml(),
      Matchers.equalTo("<valueDeserializer>vd</valueDeserializer>")
    );
  }

  @Test
  void writesRightXmlViaClass() {
    MatcherAssert.assertThat(
      "XML in right format",
      new ValueDeserializer(StringDeserializer.class).asXml(),
      Matchers.equalTo(
        "<valueDeserializer>org.apache.kafka.common.serialization.StringDeserializer</valueDeserializer>"
      )
    );
  }
}
