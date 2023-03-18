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

import com.jcabi.aspects.Immutable;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Data;
import io.github.eocqrs.kafka.Producer;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Fake Producer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */

/**
 * @todo #10:45m/DEV Fake Producer implementation
 */
@Immutable
public final class FkProducer<K, X> implements Producer<K, X> {

  private final XML settings;

  public FkProducer(final XML settings) {
    this.settings = settings;
  }

  public FkProducer(final File file) throws FileNotFoundException {
    this(
      new XMLDocument(file)
    );
  }

  @Override
  public void send(final K key, final Data<X> message) {
    try {
      new Xembler(
        new Directives()
          .add("chunks")
          .add("chunk")
          .add("topic")
          .set(message.topic())
          .up()
          .add("partition")
          .set(message.partition())
          .up()
          .add("key")
          .set(key)
          .up()
          .add("value")
          .set(message.dataized().dataize())
      ).xml();
    } catch (final ImpossibleModificationException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void close() {
  }
}
