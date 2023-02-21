package org.eocqrs.kafka;

import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since $0.id
 */
@RequiredArgsConstructor
public final class KfData<X> implements Data<X> {

  private final X data;
  private final String topic;
  private final int partition;

  @Override
  public Dataized<X> dataized() {
    return new KfDataized<>(
      this.data
    );
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