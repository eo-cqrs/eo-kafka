package org.eocqrs.kafka;

import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since $0.id
 */
@RequiredArgsConstructor
public final class KfData<X> implements Data<X> {

  private final String topic;
  private final int partition;

  /**
   * @todo #2:30m/DEV KfData dataization
   */
  @Override
  public Dataized<X> dataized() {
    return null;
  }

  @Override
  public String topic() {
    return this.topic;
  }

  @Override
  public int partition() {
    return this.partition;
  }
}