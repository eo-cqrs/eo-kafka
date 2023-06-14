package io.github.eocqrs.kafka.fake;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Fake Metadata Future Task.
 * Instead attacking real broker, returns data from {@link RecordMetadata}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
public final class FkMetadataTask extends FutureTask<RecordMetadata> {

  /**
   * Metadata.
   */
  private final RecordMetadata metadata;

  /**
   * Ctor.
   *
   * @param mtdt RecordMetadata
   */
  public FkMetadataTask(
    final RecordMetadata mtdt
  ) {
    super(() -> mtdt);
    this.metadata = mtdt;
  }

  @Override
  public RecordMetadata get() throws InterruptedException, ExecutionException {
    return this.metadata;
  }
}
