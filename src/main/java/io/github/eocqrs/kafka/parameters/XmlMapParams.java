package io.github.eocqrs.kafka.parameters;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.xml.TextXpath;
import lombok.RequiredArgsConstructor;
import org.cactoos.Scalar;
import org.cactoos.text.Concatenated;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @todo #154:30m/DEV /Tests for {@link io.github.eocqrs.kafka.parameters.XmlMapParams}.
 * Write unit tests.
 */
/**
 * It takes an XML document and a customer name, and returns a map of the customer's data.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
final class XmlMapParams implements Scalar<Map<String, Object>> {

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

  @Override
  public Map<String, Object> value() throws Exception {
    final String parent = this.customer.toString().toLowerCase(Locale.ROOT);
    return new XMLDocument(this.configuration.toString())
      .nodes(new Concatenated("//", parent, "/*").toString())
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
