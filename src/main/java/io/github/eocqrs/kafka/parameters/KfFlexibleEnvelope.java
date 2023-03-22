package io.github.eocqrs.kafka.parameters;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Params;
import org.cactoos.Input;
import org.cactoos.io.ResourceOf;

/**
 * Envelope for {@link ConsumerSettings}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public abstract class KfFlexibleEnvelope<K, X> implements ConsumerSettings<K, X> {

  /**
   * Settings in XML.
   */
  protected final XML settings;

  /**
   * A constructor that takes a Settings object and converts it to XMLDocument.
   *
   * @param params The settings object.
   */
  protected KfFlexibleEnvelope(final Params params) {
    this.settings = new XMLDocument(params.asXml());
  }

  /**
   * A constructor that takes a String and converts it to ResourceOf.
   *
   * @param name Name of resource.
   * @throws Exception When something went wrong.
   */
  protected KfFlexibleEnvelope(final String name) throws Exception {
    this(new ResourceOf(name));
  }

  /**
   * A constructor that takes an Input and converts it to XMLDocument.
   *
   * @param resource Resource with settings.
   * @throws Exception When something went wrong.
   */
  protected KfFlexibleEnvelope(final Input resource) throws Exception {
    this(new XMLDocument(resource.stream()));
  }

  /**
   * Primary ctor.
   *
   * @param settings Settings as XML.
   */
  protected KfFlexibleEnvelope(final XML settings) {
    this.settings = settings;
  }
}
