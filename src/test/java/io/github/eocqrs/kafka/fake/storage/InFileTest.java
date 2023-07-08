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

package io.github.eocqrs.kafka.fake.storage;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Test case for {@link InFile}
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
@SuppressWarnings("deprecation")
final class InFileTest {

  @Test
  void createsStorageInXmlFile() throws Exception {
    final FkStorage storage = new InFile();
    MatcherAssert.assertThat(
      "Storage %s has root <broker> tag".formatted(storage.xml()),
      storage.xml().nodes("broker").isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void appliesDirectives() throws Exception {
    final FkStorage storage = new InFile();
    storage.apply(
      new Directives()
        .xpath("/broker")
        .addIf("servers")
    );
    MatcherAssert.assertThat(
      "Storage %s contains servers tag".formatted(storage.xml()),
      storage.xml().nodes("broker/servers").isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void locksAndUnlocks() throws Exception {
    final FkStorage storage = new InFile();
    assertAll(
      () -> Assertions.assertDoesNotThrow(storage::lock),
      () -> Assertions.assertDoesNotThrow(storage::unlock),
      () -> "%s locks and unlocks without exceptions".formatted(storage.xml())
    );
  }
}