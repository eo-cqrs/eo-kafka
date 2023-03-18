package io.github.eocqrs.kafka;

/**
 * Kafka Consumer Group id
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class GroupId implements SettingsAttribute {

  /**
   * Group id value.
   */
  private final String value;

  /**
   * Ctor.
   *
   * @param val group id as string.
   */
  public GroupId(final String val) {
    this.value = val;
  }

  @Override
  public String name() {
    return "group.id";
  }

  @Override
  public String asXml() {
    return "<groupId>" + this.value + "</groupId>";
  }
}
