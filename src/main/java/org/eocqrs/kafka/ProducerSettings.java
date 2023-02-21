package org.eocqrs.kafka;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
public interface ProducerSettings {

  String bootstrapServers();

  String keyClass();

  String valueClass();
}