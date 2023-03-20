package io.github.eocqrs.kafka.settings;

/**
 * It's a wrapper for the `security.protocol` kafka attribute.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.2
 */
public final class SecurityProtocol extends AttrEnvelope {

  /**
   * Ctor.
   *
   * @param val The value.
   */
  public SecurityProtocol(final String val) {
    super(val, "security.protocol");
  }
}
