/*
 *  Copyright (c) 2022 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.eocqrs.kafka.fake;

import com.jcabi.log.Logger;
import io.github.eocqrs.kafka.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Fake Consumer.
 *
 * @param <K> The key
 * @param <X> The value
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
public final class FkConsumer<K, X> implements Consumer<K, X> {

  /**
   * Client id.
   */
  private final UUID id;
  /**
   * Broker.
   */
  private final FkBroker broker;

  /**
   * Ctor.
   *
   * @param identifier UUID id
   * @param brkr       FkBroker
   */
  public FkConsumer(final UUID identifier, final FkBroker brkr) {
    this.id = identifier;
    this.broker = brkr;
  }

  /*
   * @todo #54:60m/DEV Fake subscribe is not implemented
   */
  @Override
  public void subscribe(final String... topics) {
    throw new UnsupportedOperationException("#subscribe()");
  }

  @Override
  public void subscribe(final Collection<String> topics) {
    throw new UnsupportedOperationException("#subscribe()");
  }

  /*
   * @todo #54:60m/DEV Fake subscribe with
   *    ConsumerRebalanceListener is not implemented
   */
  @Override
  public void subscribe(final ConsumerRebalanceListener listener,
                        final String... topics) {
    throw new UnsupportedOperationException("#subscribe()");
  }

  /*
   * @todo #54:60m/DEV Fake records is not implemented
   */
  @Override
  public ConsumerRecords<K, X> records(
    final String topic, final Duration timeout
  ) {
    throw new UnsupportedOperationException("#records()");
  }

  /*
   * @todo #54:60m/DEV Fake unsubscribe is not implemented
   */
  @Override
  public void unsubscribe() {
    throw new UnsupportedOperationException("#unsubscribe()");
  }

  @Override
  public void close() {
    Logger.info(
      this, "Consumer %s closed at %s"
        .formatted(
          this.id,
          LocalDateTime.now(
            Clock.systemUTC()
          )
        )
    );
  }
}
