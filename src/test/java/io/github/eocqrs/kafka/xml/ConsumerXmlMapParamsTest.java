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

package io.github.eocqrs.kafka.xml;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ConsumerXmlMapParams}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
final class ConsumerXmlMapParamsTest {

  private XML xml;

  @BeforeEach
  void setUp() throws FileNotFoundException {
    this.xml = new XMLDocument(new File("src/test/resources/consumer.xml"));
  }

  @Test
  void generatesRightConsumerBootstrapServers() throws Exception {
    final Map<String, Object> map =
      new ConsumerXmlMapParams(this.xml)
        .value();
    MatcherAssert.assertThat(
      "Consumer bootstrap.servers in right format",
      map.get("bootstrap.servers"),
      Matchers.equalTo("localhost:9092")
    );
  }

  @Test
  void generatesRightKeyDeserializer() throws Exception {
    final Map<String, Object> map =
      new ConsumerXmlMapParams("src/test/resources/consumer.xml")
        .value();
    MatcherAssert.assertThat(
      "Consumer key.deserializer in right format",
      map.get("key.deserializer"),
      Matchers.equalTo("org.apache.kafka.common.serialization.StringDeserializer")
    );
  }

  @Test
  void generatesRightValueDeserializer() throws Exception {
    final Map<String, Object> map =
      new ConsumerXmlMapParams(
        new ResourceOf("src/test/resources/consumer.xml")
      ).value();
    MatcherAssert.assertThat(
      "Consumer value.deserializer in right format",
      map.get("value.deserializer"),
      Matchers.equalTo("org.apache.kafka.common.serialization.StringDeserializer")
    );
  }

  @Test
  void generatesRightGroupId() throws Exception {
    final Map<String, Object> map =
      new ConsumerXmlMapParams(this.xml)
        .value();
    MatcherAssert.assertThat(
      "Consumer group.id in right format",
      map.get("group.id"),
      Matchers.equalTo("1")
    );
  }
}
