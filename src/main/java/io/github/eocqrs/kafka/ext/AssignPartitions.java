package io.github.eocqrs.kafka.ext;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.cactoos.list.ListOf;

import java.util.Collection;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.5
 */
public final class AssignPartitions implements Action {

  private final KafkaConsumer<?, ?> consumer;
  private final Collection<TopicPartition> partitions;

  public AssignPartitions(
    final KafkaConsumer<?, ?> cnsmr,
    TopicPartition... prts
  ) {
    this.consumer = cnsmr;
    this.partitions = new ListOf<>(prts);
  }

  @Override
  public void apply() {
    this.consumer.assign(this.partitions);
  }
}
