package io.github.eocqrs.kafka;

/**
 * Kafka Key Serializer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class KeySerializer implements SettingsAttribute {

  /**
   * Key serializer value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val key serializer as string.
   */
  public KeySerializer(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "key.serializer";
  }

  @Override
  public String asXml() {
    return "<keySerializer>" + this.value + "</keySerializer>";
  }
}
