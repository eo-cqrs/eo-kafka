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

/**
 * It's a wrapper for the `auto.offset.reset` kafka attribute.
 * <p>
 * What to do when there is no initial offset in Kafka or
 * if the current offset does not exist any more on the server
 * (e.g., because that data has been deleted):
 * <strong>earliest</strong>,
 * <strong>latest</strong>,
 * <strong>none</strong>
 * </p>
 * <p>
 * The default is latest.
 * </p>
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.3
 */
public final class AutoOffsetReset extends KfAttrEnvelope {

  /**
   * Ctor.
   *
   * @param value The value.
   */
  public AutoOffsetReset(final String value) {
    super(value, "auto.offset.reset");
  }
}
