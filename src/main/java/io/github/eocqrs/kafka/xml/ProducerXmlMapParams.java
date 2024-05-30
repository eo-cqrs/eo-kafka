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

package io.github.eocqrs.kafka.xml;

import com.jcabi.xml.XML;
import org.cactoos.Input;

/**
 * Producer XML Params.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class ProducerXmlMapParams extends XmlMapParams {

  /**
   * Ctor.
   *
   * @param config XML config.
   */
  public ProducerXmlMapParams(final XML config) {
    super(config, KfCustomer.PRODUCER);
  }

  /**
   * Ctor.
   *
   * @param resource The resource with xml settings.
   * @throws Exception When something went wrong.
   */
  ProducerXmlMapParams(final Input resource) throws Exception {
    super(resource, KfCustomer.PRODUCER);
  }

  /**
   * Ctor.
   *
   * @param name Name of the resource.
   * @throws Exception When something went wrong.
   */
  ProducerXmlMapParams(final String name) throws Exception {
    super(name, KfCustomer.PRODUCER);
  }
}
