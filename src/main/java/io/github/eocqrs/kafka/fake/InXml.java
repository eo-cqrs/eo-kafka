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

package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.kafka.Data;
import io.github.eocqrs.xfake.FkStorage;
import org.cactoos.list.ListOf;
import org.xembly.Directives;

import java.util.Collection;

/**
 * XML Kafka Broker.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
public final class InXml implements FkBroker {

  /**
   * Storage.
   */
  private final FkStorage storage;

  /**
   * Ctor.
   *
   * @param strg Storage.
   * @throws Exception When something went wrong.
   */
  public InXml(final FkStorage strg)
    throws Exception {
    this.storage = strg;
    this.storage.apply(
      new Directives()
        .xpath("broker")
        .addIf("topics")
    );
  }

  @Override
  public <X> FkBroker withDataset(
    final Object key,
    final Data<X> data
  ) throws Exception {
    this.storage.apply(
      new Directives()
        .xpath(
          "broker/topics/topic[name = '%s']"
            .formatted(
              data.topic()
            )
        )
        .addIf("datasets")
        .add("dataset")
        .add("partition")
        .set(data.partition())
        .up()
        .addIf("key")
        .set(key)
        .up()
        .addIf("value")
        .set(data.dataized().dataize())
    );
    return this;
  }

  @Override
  public FkBroker withTopics(final String... topics) {
    new ListOf<>(topics)
      .forEach(
        topic -> {
          try {
            this.storage.apply(
              new Directives()
                .xpath("broker/topics")
                .add("topic")
                .addIf("name")
                .set(topic)
                .up()
                .addIf("datasets")
            );
          } catch (final Exception ex) {
            throw new IllegalStateException(
              "Topics can't be applied",
              ex
            );
          }
        }
      );
    return this;
  }

  @Override
  public Collection<String> data(final String query) throws Exception {
    return this.storage.xml().xpath(query);
  }
}
