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

import io.github.eocqrs.kafka.data.KfData;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link DatasetDirs}.
 *
 * @author Aliaksei Bialiauski (abialaiuski.dev@gmail.com)
 * @since 0.3.5
 */
@SuppressWarnings("deprecation")
final class DatasetDirsTest {

  @Test
  void dirsInRightFormat() throws Exception {
    final String directives = "XPATH \"broker/topics/topic[name = &apos;&apos;]\";ADDIF "
      + "\"datasets\";ADD \"dataset\";ADD \"partition\";\n"
      + "4:SET \"0\";UP;ADDIF \"key\";SET \"test\";UP;ADDIF \"value\";SET \"\";"
      + "UP;ADDIF \"seen\";SET \"false\";";
    MatcherAssert.assertThat(
      "Directives in the right format",
      new DatasetDirs<>(
        "test",
        new KfData<>("", "", 0)
      ).value().toString(),
      Matchers.equalTo(
        directives
      )
    );
  }
}
