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
import org.cactoos.list.ListOf;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
/*
 * @todo #303:90m/DEV fake consumer is not support various data types
 */

/**
 * Fake Consumer.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.0
 */
public final class FkConsumer implements Consumer<Object, String> {

  /**
   * Consumer id.
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

  @Override
  public void subscribe(final String... topics) {
    this.subscribe(new ListOf<>(topics));
  }

  @Override
  public void subscribe(final Collection<String> topics) {
    topics.forEach(
      t -> {
        try {
          this.broker.with(new SubscribeDirs(t, this.id).value());
        } catch (final Exception ex) {
          throw new IllegalStateException(ex);
        }
      }
    );
  }

  @Override
  public void subscribe(final ConsumerRebalanceListener listener,
                        final String... topics) {
    new ListOf<>(topics)
      .forEach(t -> {
        try {
          this.broker.with(
            new WithRebalanceListener(
              new SubscribeDirs(t, this.id),
              listener
            ).value());
        } catch (final Exception ex) {
          throw new IllegalStateException(ex);
        }
      });
  }

  /*
   * @todo #303:45m/DEV records timeout is not implemented
   */
  @Override
  public ConsumerRecords<Object, String> records(
    final String topic, final Duration timeout
  ) throws Exception {
    this.broker.with(new SubscribeDirs(topic, this.id).value());
    final ConsumerRecords<Object, String> records =
      new FkRecords(
        topic,
        this.broker.data(
          ("broker/topics/topic[name = '%s']/datasets"
            + "/dataset[seen = 'false']/value/text()"
          ).formatted(
            topic
          )
        )
      ).value();
    records.forEach(rec -> {
      try {
        this.broker.with(new SeenDirs(topic, rec.value()).value());
      } catch (final Exception ex) {
        throw new IllegalStateException(ex);
      }
    });
    return records;
  }

  @Override
  public void unsubscribe() throws Exception {
    while (
      !this.broker.data(
        "broker/subs/sub[consumer = '%s']/consumer/text()"
          .formatted(
            this.id
          )
      ).isEmpty()
    ) {
      this.broker.with(new UnsubscribeDirs(this.id).value());
    }
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
