package io.github.eocqrs.kafka.parameters;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.xml.TextXpath;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.cactoos.text.Concatenated;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @todo #154:30m/DEV Get rid of deprecated api.
 * We have to remove <b>all</b> deprecated api, before release `0.0.3`.
 */
/**
 * Allow creating custom Consumer/Producer from XML.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public final class KfXmlFlexible<K, X> extends KfFlexibleEnvelope<K, X>
  implements ConsumerSettings<K, X>, ProducerSettings<K, X> {

  /**
   * It's a regex that matches all capital letters, except the first one.
   */
  private static final Pattern CAPITALS = Pattern.compile("(?<!^)([A-Z])");

  /**
   * Ctor.
   *
   * @param name Name of XML configuration placed in resources folder.
   * @throws Exception When something went wrong.
   */
  public KfXmlFlexible(final String name) throws Exception {
    super(name);
  }

  @Override
  public KafkaConsumer<K, X> consumer() {
    return new KafkaConsumer<K, X>(this.xmlConfigAsMap("consumer"));
  }

  @Override
  public KafkaProducer<K, X> producer() {
    return new KafkaProducer<K, X>(this.xmlConfigAsMap("producer"));
  }

  /**
   * It takes a string, uses it to find a bunch of nodes in an XML document,
   * then returns a map of the kafka-names to their values.
   *
   * @param type The type of the configuration to be loaded.
   * @return A map of the XML configuration file.
   */
  private Map<String, Object> xmlConfigAsMap(final String type) {
    return new XMLDocument(this.settings.toString())
      .nodes(new Concatenated("//", type, "/*").toString())
      .stream()
      .map(Object::toString)
      .map(XMLDocument::new)
      .map(xml -> xml.nodes("//*").get(0).node().getNodeName())
      .collect(
        Collectors.toMap(
          name -> KfXmlFlexible.CAPITALS
            .matcher(name)
            .replaceAll(".$1")
            .toLowerCase(Locale.ROOT),
          name -> new TextXpath(this.settings, "//".concat(name)).toString()
        )
      );
  }
}
