package io.github.eocqrs.kafka;

/**
 * Kafka Key Deserializer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class KeyDeserializer implements SettingsAttribute {

  /**
   * Key deserializer value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val key deserializer as string.
   */
  public KeyDeserializer(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "key.deserializer";
  }

  @Override
  public String asXml() {
    return "<keyDeserializer>" + this.value + "</keyDeserializer>";
  }
}
