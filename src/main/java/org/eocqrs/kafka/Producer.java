package org.eocqrs.kafka;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since $0.id
 */
public interface Producer<K, X> {

  void send(K key, Data<X> message);
}