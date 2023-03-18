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

package io.github.eocqrs.kafka.consumer;

import com.jcabi.aspects.Immutable;
import com.jcabi.xml.XML;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.xml.TextXpath;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer Settings.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
@RequiredArgsConstructor
public final class KfConsumerSettings<K, X> implements ConsumerSettings<K, X> {

  /**
   * Settings in XML.
   */
  private final XML settings;

  @Override
  public KafkaConsumer<K, X> consumer() {
    final Map<String, Object> config = new HashMap<>(4);
    config.put(
      "bootstrap.servers",
      new TextXpath(
        this.settings, "//bootstrapServers"
      ).toString()
    );
    config.put(
      "group.id",
      new TextXpath(
        this.settings, "//groupId"
      ).toString()
    );
    config.put(
      "key.deserializer",
      new TextXpath(
        this.settings, "//keyDeserializer"
      ).toString()
    );
    config.put(
      "value.deserializer",
      new TextXpath(
        this.settings, "//valueDeserializer"
      ).toString()
    );
    return new KafkaConsumer<>(config);
  }
}
