package io.github.eocqrs.kafka;

/**
 * Kafka Value Deserializer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class ValueDeserializer implements SettingsAttribute {

  /**
   * Value deserializer value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val value deserializer as string.
   */
  public ValueDeserializer(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "value.deserializer";
  }

  @Override
  public String asXml() {
    return "<valueDeserializer>" + this.value + "</valueDeserializer>";
  }
}
