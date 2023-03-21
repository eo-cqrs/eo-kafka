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

package io.github.eocqrs.kafka.producer.settings;

import com.jcabi.xml.XML;
import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import io.github.eocqrs.kafka.xml.TextXpath;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.Input;

import java.util.HashMap;
import java.util.Map;


/**
 * Kafka Consumer Settings.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @see io.github.eocqrs.kafka.parameters.KfFlexibleSettings
 * @deprecated You should use {@link io.github.eocqrs.kafka.parameters.KfFlexibleSettings}
 * instead of {@link KfConsumerSettings}.
 * @since 0.0.0
 */
@Deprecated(since = "0.0.2", forRemoval = true)
public final class KfProducerSettings<K, X> extends KfProducerSettingsEnvelope<K, X> {

  /**
   * A ctor takes a Params object.
   *
   * @param params Kafka parameters.
   */
  public KfProducerSettings(final Params params) {
    super(params);
  }

  /**
   * A ctor that takes a String and converts it to Input.
   *
   * @param name Name of xml configuration.
   * @throws Exception When something went wrong.
   */
  public KfProducerSettings(final String name) throws Exception {
    super(name);
  }

  /**
   * A ctor that takes an Input and converts it to XMLDocument.
   *
   * @param resource Resources.
   * @throws Exception When something went wrong.
   */
  public KfProducerSettings(final Input resource) throws Exception {
    super(resource);
  }

  /**
   * Primary ctor.
   *
   * @param settings Settings as XML.
   */
  public KfProducerSettings(final XML settings) {
    super(settings);
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
