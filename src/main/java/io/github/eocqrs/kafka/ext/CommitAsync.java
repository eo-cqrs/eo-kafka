package io.github.eocqrs.kafka.ext;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.5
 */
@RequiredArgsConstructor
public final class CommitAsync implements Action {

  private final KafkaConsumer<?, ?> consumer;

  @Override
  public void apply() {
    this.consumer.commitAsync();
  }
}
