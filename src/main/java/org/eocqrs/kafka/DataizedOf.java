package org.eocqrs.kafka;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */

/**
 * @todo #19:30m/DEV DataizedOf implementation
 */
public final class DataizedOf<T> implements Dataized<T> {

  /**
   * @todo #20:15m/DEV DataizedOf Ctor
   */
  public DataizedOf(Dataized<T> dataized, String format) {

  }

  @Override
  public T dataize() {
    return null;
  }
}