package org.eocqrs.kafka.fake;

import org.eocqrs.kafka.Data;
import org.eocqrs.kafka.Producer;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */

/**
 * @todo #10:45m/DEV Fake Producer implementation
 */
public class FkProducer<K, X> implements Producer<K, X> {

  @Override
  public void send(final K key, final Data<X> message) {
    throw new UnsupportedOperationException("#send()");
  }
}