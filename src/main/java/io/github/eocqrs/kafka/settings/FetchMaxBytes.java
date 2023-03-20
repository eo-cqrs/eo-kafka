package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.SettingsAttribute;

/**
 * @author Aliaksei Bialiauski ()
 * @since 0.0.2
 */
public final class FetchMaxBytes implements SettingsAttribute {

  @Override
  public String name() {
    throw new UnsupportedOperationException("#name()");
  }

  @Override
  public String asXml() {
    throw new UnsupportedOperationException("#asXml()");
  }
}
