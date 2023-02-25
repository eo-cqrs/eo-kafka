package org.eocqrs.kafka;

public interface Consumer<X> {

  void consume(int partition, Data<X> message);

  Data<X> dataize();

}
