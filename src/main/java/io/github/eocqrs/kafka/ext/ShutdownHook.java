package io.github.eocqrs.kafka.ext;

import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.5
 */
@RequiredArgsConstructor
public final class ShutdownHook implements Action {

  /**
   * Thread.
   */
  private final Thread thread;

  @Override
  public void apply() {
    Runtime.getRuntime().addShutdownHook(
      this.thread
    );
  }
}
