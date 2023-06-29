/*
 *  Copyright (c) 2023 Aliaksei Bialiauski, EO-CQRS
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

package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.kafka.Data;
import org.cactoos.Scalar;
import org.xembly.Directives;

/**
 * Dataset Directives.
 *
 * @param <K> Dataset key type
 * @param <X> Dataset data type
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
@SuppressWarnings("deprecation")
public final class DatasetDirs<K, X> implements Scalar<Directives> {

  /**
   * Dataset Key.
   */
  private final K key;
  /**
   * Dataset Data.
   */
  private final Data<X> data;

  /**
   * Ctor.
   *
   * @param key  Key
   * @param data Data
   */
  public DatasetDirs(final K key, final Data<X> data) {
    this.key = key;
    this.data = data;
  }

  @Override
  public Directives value() throws Exception {
    return new Directives()
      .xpath(
        "broker/topics/topic[name = '%s']"
          .formatted(
            this.data.topic()
          )
      )
      .addIf("datasets")
      .add("dataset")
      .add("partition")
      .set(this.data.partition())
      .up()
      .addIf("key")
      .set(this.key)
      .up()
      .addIf("value")
      .set(this.data.dataized().dataize())
      .up()
      .addIf("seen")
      .set("false");
  }
}
