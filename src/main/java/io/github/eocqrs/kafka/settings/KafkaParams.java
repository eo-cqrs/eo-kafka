package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.Settings;
import io.github.eocqrs.kafka.SettingsAttribute;
import org.cactoos.list.ListOf;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public final class KafkaParams implements Settings {

  private final Collection<SettingsAttribute> params;

  public KafkaParams(final SettingsAttribute... args) {
    this.params = new ListOf<>(args);
  }

  @Override
  public Collection<SettingsAttribute> all() {
    return Collections.unmodifiableCollection(this.params);
  }

  @Override
  public String asXml() {
    return this.params
      .stream()
      .map(SettingsAttribute::asXml)
      .collect(Collectors.joining("\n"));
  }
}
