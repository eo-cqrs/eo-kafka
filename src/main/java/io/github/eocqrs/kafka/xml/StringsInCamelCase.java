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

import lombok.RequiredArgsConstructor;
import org.cactoos.Scalar;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;

import java.util.Collection;
import java.util.List;

/**
 * It takes a collection of strings
 * and returns a list of strings where
 * the first letter of each string is capitalized.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
public final class StringsInCamelCase implements Scalar<List<String>> {

  /**
   * The origin.
   */
  private final Collection<String> origin;

  @Override
  public List<String> value() {
    return this.origin.stream()
      .map(
        word -> {
          final char first = word.charAt(0);
          return word.replaceFirst(
            new TextOf(first).toString(),
            new Upper(new TextOf(first)).toString()
          );
        }
      ).toList();
  }
}
