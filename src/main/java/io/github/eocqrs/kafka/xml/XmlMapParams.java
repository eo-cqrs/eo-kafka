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

package io.github.eocqrs.kafka.xml;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.io.ResourceOf;
import org.cactoos.text.FormattedText;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * It takes an XML document and a customer name, and returns a map of the customer's data.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
abstract class XmlMapParams implements Scalar<Map<String, Object>> {

  /**
   * It's a regex that matches all capital letters, except the first one.
   */
  private static final Pattern CAPITALS = Pattern.compile("(?<!^)([A-Z])");

  /**
   * The origin config.
   */
  private final XML configuration;

  /**
   * Consumer or Producer.
   */
  private final KfCustomer customer;

  /**
   * Ctor.
   *
   * @param config XML config.
   * @param cust   Customer type.
   */
  protected XmlMapParams(final XML config, final KfCustomer cust) {
    this.configuration = config;
    this.customer = cust;
  }

  /**
   * A ctor that takes an Input and converts it to XMLDocument.
   *
   * @param resource Resource with settings.
   * @param cust  Customer type.
   * @throws Exception When something went wrong.
   */
  protected XmlMapParams(final Input resource, final KfCustomer cust) throws Exception {
    this(new XMLDocument(resource.stream()), cust);
  }

  /**
   * A constructor that takes a String and converts it to ResourceOf.
   *
   * @param name Name of resource.
   * @param cust Customer type.
   * @throws Exception When something went wrong.
   */
  protected XmlMapParams(final String name, final KfCustomer cust) throws Exception {
    this(new ResourceOf(name), cust);
  }

  @Override
  public final Map<String, Object> value() throws Exception {
    return new XMLDocument(this.configuration.toString())
      .nodes(new FormattedText("//%s/*", this.customer).toString())
      .stream()
      .map(Object::toString)
      .map(XMLDocument::new)
      .map(xml -> xml.nodes("//*").get(0).node().getNodeName())
      .collect(
        Collectors.toMap(
          name -> XmlMapParams.CAPITALS
            .matcher(name)
            .replaceAll(".$1")
            .toLowerCase(Locale.ROOT),
          name -> new TextXpath(this.configuration, "//".concat(name)).toString()
        )
      );
  }
}
