package io.github.eocqrs.kafka.consumer.settings;

import io.github.eocqrs.kafka.Settings;
import io.github.eocqrs.kafka.SettingsAttribute;
import lombok.RequiredArgsConstructor;
import org.cactoos.text.FormattedText;

import java.util.Collection;

/**
 * Kafka params for {@link KfConsumerSettings}.
 * Decorates {@link Settings}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
public final class KfConsumerParams implements Settings {

  /**
   * The origin.
   */
  private final Settings origin;

  @Override
  public Collection<SettingsAttribute> all() {
    return this.origin.all();
  }

  @Override
  public String asXml() {
    return new FormattedText(
      "<consumer>\n%s\n</consumer>\n",
      this.origin.asXml()
    ).toString();
  }
}
