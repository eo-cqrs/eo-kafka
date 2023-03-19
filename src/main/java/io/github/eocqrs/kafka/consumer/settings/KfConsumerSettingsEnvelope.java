package io.github.eocqrs.kafka.consumer.settings;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.Settings;
import io.github.eocqrs.kafka.SettingsAttribute;
import org.cactoos.Input;
import org.cactoos.io.ResourceOf;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Envelope for {@link ConsumerSettings}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public abstract class KfConsumerSettingsEnvelope<K, X> implements ConsumerSettings<K, X> {

  /**
   * Settings in XML.
   */
  protected final XML settings;

  /**
   * A constructor that takes a Settings object and converts it to XMLDocument.
   *
   * @param settings The settings object.
   */
  protected KfConsumerSettingsEnvelope(final Settings settings) {
    this.settings = new XMLDocument(settings.asXml());
  }

  /**
   * A constructor that takes a String and converts it to ResourceOf.
   *
   * @param name Name of resource.
   * @throws Exception When something went wrong.
   */
  protected KfConsumerSettingsEnvelope(final String name) throws Exception {
    this(new ResourceOf(name));
  }

  /**
   * A constructor that takes an Input and converts it to XMLDocument.
   *
   * @param resource Resource with settings.
   * @throws Exception When something went wrong.
   */
  protected KfConsumerSettingsEnvelope(final Input resource) throws Exception {
    this(new XMLDocument(resource.stream()));
  }

  /**
   * Primary ctor.
   *
   * @param settings Settings as XML.
   */
  protected KfConsumerSettingsEnvelope(final XML settings) {
    this.settings = settings;
  }
}
