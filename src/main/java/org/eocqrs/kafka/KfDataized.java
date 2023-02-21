package org.eocqrs.kafka;

import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public final class KfDataized<X> implements Dataized<X> {

  private final X data;

  @Override
  public X dataize() {
    return this.data;
  }
}