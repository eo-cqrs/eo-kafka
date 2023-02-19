package org.eocqrs.kafka;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
public interface Data<X> {

  Dataized<X> dataized();

  String topic();

  int partition();
}