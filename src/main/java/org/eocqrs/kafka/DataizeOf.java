package org.eocqrs.kafka;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
public class DataizeOf<T> implements Dataized<T> {

  public DataizeOf(Dataized<T> dataized, String format) {

  }

  @Override
  public T dataize() {
    return null;
  }
}