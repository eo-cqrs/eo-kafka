package io.github.eocqrs.kafka.fake;

import java.io.Serial;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Immutable Lock.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
final class ImmutableReentrantLock extends ReentrantLock {

  /**
   * Serialization id.
   */
  @Serial
  private static final long serialVersionUID = -3003135962114984635L;
}
