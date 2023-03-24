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

package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link MapParams}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
final class MapParamsTest {

  @Test
  void mapsValueDeserializerInRightFormat() {
    final Map<String, Object> map = new MapParams(
      new KfConsumerParams(
        new KfParams(
          new ValueDeserializer("com.solvd.serialization.JsonSerializer")
        )
      )
    ).value();
    MatcherAssert.assertThat(
      "Object ValueDeserializer in right format",
      map.get("value.deserializer"),
      Matchers.equalTo("com.solvd.serialization.JsonSerializer")
    );
  }

  @Test
  void mapsMultiplePropertiesInRightFormat() {
    final Map<String, Object> map = new MapParams(
      new KfConsumerParams(
        new KfParams(
          new GroupId("1"),
          new BootstrapServers("broker:9092")
        )
      )
    ).value();
    MatcherAssert.assertThat(
      "Object GroupId in right format",
      map.get("group.id"),
      Matchers.equalTo("1")
    );
    MatcherAssert.assertThat(
      "Object BootstrapServers in right format",
      map.get("bootstrap.servers"),
      Matchers.equalTo("broker:9092")
    );
  }
}
