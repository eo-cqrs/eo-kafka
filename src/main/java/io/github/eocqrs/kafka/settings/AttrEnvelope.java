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

package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.ParamsAttribute;
import org.cactoos.text.FormattedText;

/**
 * It's a wrapper for a string value that can be converted to XML.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public abstract class AttrEnvelope implements ParamsAttribute {

  protected final String value;

  protected final String name;

  protected AttrEnvelope(final String value, final String name) {
    this.value = value;
    this.name = name;
  }

  @Override
  public final String asXml() {
    return new FormattedText(
      "<%s>%s</%s>",
      this.nameInCamelCase(),
      this.value,
      this.nameInCamelCase()
    ).toString();
  }

  @Override
  public final String name() {
    return this.name;
  }

  /**
   * Name to camelcase.
   *
   * @return The name of the attribute as a tag.
   */
  /*
  * #134:60m/DEV Replace a private method with object.
  * We should implement something like NameInCamelCase.
  * Or even send a pull request to cactoos
  * with an implementation of TextInCamelCase.
  * */
  private String nameInCamelCase() {
    final String[] result = this.name.split("\\.");
    final char first = result[1].charAt(0);
    result[1] = result[1].replaceFirst(
      String.valueOf(first),
      String.valueOf(Character.toUpperCase(first))
    );
    return String.join("", result);
  }
}
