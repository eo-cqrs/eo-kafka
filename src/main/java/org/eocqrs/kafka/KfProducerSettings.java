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

package org.eocqrs.kafka;

import com.jcabi.xml.XML;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.eocqrs.kafka.xml.TextXpath;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
@RequiredArgsConstructor
public final class KfProducerSettings<K, X> implements ProducerSettings<K, X> {

  private final XML xml;

  @Override
  public KafkaProducer<K, X> producer() {
    final Map<String, Object> config = new HashMap<>(3);
    config.put("bootstrap.servers",
      new TextXpath(this.xml, "//bootstrapServers")
        .toString()
    );
    config.put("key.serializer",
      new TextXpath(this.xml, "//keySerializer")
        .toString()
    );
    config.put("value.serializer",
      new TextXpath(this.xml, "//valueSerializer")
        .toString()
    );
    return new KafkaProducer<>(config);
  }
}