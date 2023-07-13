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

import io.github.eocqrs.kafka.ConsumerSettings;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Yaml configuration for consumer.
 *
 * @param <K> The key type.
 * @param <X> The value type.
 */
public final class KfYamlConsumerSettings<K, X>
  implements ConsumerSettings<K, X> {

  /**
   * YAML params.
   */
  private final YamlMapParams params;

  /**
   * Ctor.
   *
   * @param prms YAML Params
   */
  public KfYamlConsumerSettings(final YamlMapParams prms) {
    this.params = prms;
  }

  /**
   * Ctor.
   *
   * @param nm YAML file name
   * @throws Exception when something went wrong
   */
  public KfYamlConsumerSettings(final String nm) throws Exception {
    this(new YamlMapParams(nm));
  }

  @Override
  public KafkaConsumer<K, X> consumer() {
    return new KafkaConsumer<>(this.params.value());
  }
}
