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

package io.github.eocqrs.kafka.yaml;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Test case for {@link YamlMapParams}.
 */
final class YamlMapParamsTest {

  /**
   * Under test params.
   */
  private Map<String, Object> params;

  @BeforeEach
  void setParams() throws Exception {
    this.params = new YamlMapParams("producer.yaml").value();
  }

  @Test
  void createsMapProperly() {
    final ListOf<String> actuals = new ListOf<>(
      "bootstrap.servers",
      "key.serializer",
      "value.serializer"
    );
    assertAll(
      () ->
        MatcherAssert.assertThat(
          "\nEvery key from \n\t%s\nShould be placed in \n\t%s"
            .formatted(this.params.keySet(), actuals),
          actuals,
          Matchers.containsInAnyOrder(
            this.params.keySet().toArray()
          )
        ),
      () ->
        actuals.forEach(
          actual -> MatcherAssert.assertThat(
            "\n%s\nContains value for each key from\n\t%s"
              .formatted(this.params, actual),
            this.params.get(actual),
            Matchers.is(Matchers.notNullValue())
          )
        )
    );
  }

  @Test
  void throwsOnModification() {
    assertThrows(
      UnsupportedOperationException.class,
      () -> this.params.put("key", new Object()),
      "Must not modify \n\t%s".formatted(this.params)
    );
  }
}
