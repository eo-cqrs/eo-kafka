package io.github.eocqrs.kafka;

/**
 * Kafka Bootstrap Servers.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class BootstrapServers implements SettingsAttribute {

  /**
   * Bootstrap servers value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val bootstrap servers as string.
   */
  public BootstrapServers(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "bootstrap.servers";
  }

  @Override
  public String asXml() {
    return "<bootstrapServers>" + this.value + "</bootstrapServers>";
  }
}
