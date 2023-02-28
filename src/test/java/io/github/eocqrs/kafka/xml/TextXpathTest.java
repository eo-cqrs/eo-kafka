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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TextXpath}
 *
 * @since 0.0.0
 */
class TextXpathTest {

  @Test
  void testBootStrapServers() throws FileNotFoundException {
    final String servers = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/producer.xml")
      ),
      "//bootstrapServers"
    ).toString();
    assertThat(servers).isEqualTo("test");
  }

  @Test
  void testName() throws FileNotFoundException {
    final String name = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/test.xml")
      ),
      "//name"
    ).toString();
    assertThat(name).isEqualTo("h1alexbel");
  }

  @Test
  void testInvalidQuery() throws FileNotFoundException {
    final TextXpath xpath = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/producer.xml")
      ),
      "//invalid"
    );
    assertThrows(Exception.class, xpath::toString);
  }
}