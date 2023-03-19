package io.github.eocqrs.kafka.producer.settings;

import io.github.eocqrs.kafka.Settings;
import io.github.eocqrs.kafka.SettingsAttribute;
import lombok.RequiredArgsConstructor;
import org.cactoos.text.FormattedText;

import java.util.Collection;

@RequiredArgsConstructor
public final class KfProducerParams implements Settings {

  private final Settings origin;

  @Override
  public Collection<SettingsAttribute> all() {
    return this.origin.all();
  }

  @Override
  public String asXml() {
    return new FormattedText(
      "<producer>\n%s\n</producer>",
      this.origin.asXml()
    ).toString();
  }
}
