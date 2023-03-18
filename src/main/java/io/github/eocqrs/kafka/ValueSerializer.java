package io.github.eocqrs.kafka;

/**
 * Kafka Value Serializer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class ValueSerializer implements SettingsAttribute {

  /**
   * Value serializer value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val value serializer as string.
   */
  public ValueSerializer(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "value.serializer";
  }

  @Override
  public String asXml() {
    return "<valueSerializer>" + this.value + "</valueSerializer>";
  }
}
