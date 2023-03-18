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

package io.github.eocqrs.kafka.producer;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.xml.TextXpath;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.Input;
import org.cactoos.io.ResourceOf;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Settings.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.0
 */
public final class KfProducerSettings<K, X> implements ProducerSettings<K, X> {

  /**
   * Settings in XML.
   */
  private final XML settings;

  /**
   * A ctor that takes a String and converts it to Input.
   *
   * @param name Name of xml configuration.
   * @throws Exception When something went wrong.
   */
  public KfProducerSettings(final String name) throws Exception {
    this(new ResourceOf(name));
  }

  /**
   * A ctor that takes an Input and converts it to XMLDocument.
   *
   * @param resource Resources.
   * @throws Exception When something went wrong.
   */
  public KfProducerSettings(final Input resource) throws Exception {
    this(new XMLDocument(resource.stream()));
  }

  /**
   * Primary ctor.
   *
   * @param settings Settings as XML.
   */
  public KfProducerSettings(final XML settings) {
    this.settings = settings;
  }

  @Override
  public KafkaProducer<K, X> producer() {
    final Map<String, Object> config = new HashMap<>(3);
    config.put(
      "bootstrap.servers",
      new TextXpath(this.settings, "//bootstrapServers")
        .toString()
    );
    config.put(
      "key.serializer",
      new TextXpath(this.settings, "//keySerializer")
        .toString()
    );
    config.put(
      "value.serializer",
      new TextXpath(this.settings, "//valueSerializer")
        .toString()
    );
    return new KafkaProducer<>(config);
  }
}