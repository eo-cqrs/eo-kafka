package io.github.eocqrs.kafka.consumer.settings;

import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.ParamsAttr;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * Kafka params for {@link io.github.eocqrs.kafka.consumer.KfConsumer}.
 * Decorates {@link Params}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
@RequiredArgsConstructor
public final class KfConsumerParams implements Params {

  /**
   * The origin.
   */
  private final Params origin;

  @Override
  public Collection<ParamsAttr> all() {
    return this.origin.all();
  }

  @Override
  public String asXml() {
    return "<consumer>\n%s\n</consumer>\n"
      .formatted(this.origin.asXml());
  }
}
