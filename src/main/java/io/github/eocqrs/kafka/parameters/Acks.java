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

package io.github.eocqrs.kafka.parameters;

/**
 * It's a wrapper for the `acks` kafka attribute.
 * The number of acknowledgments the producer requires the leader
 * to have received before considering a request complete.
 * This controls the durability of records that are sent.
 * The following settings are allowed:
 * <strong>acks=0</strong>
 * <strong>acks=1</strong>
 * <strong>acks=all</strong> or <strong>acks=-1</strong>
 *
 * <p>
 * <strong>acks=0</strong>
 * <br>
 * If set to zero, then the producer will not wait for any acknowledgment from the server at all.
 * The record will be immediately added to the socket buffer and considered sent. No guarantee can be made that the server has received the record in this case, and the retries configuration will not take effect (as the client won't generally know of any failures).
 * The offset given back for each record will always be set to -1.
 * </p>
 *
 * <p>
 * <strong>acks=1</strong>
 * <br>
 * This will mean the leader will write the record to its local log but will respond without awaiting full acknowledgment from all followers.
 * In this case should the leader fail immediately after acknowledging the record, but before the followers have replicated it, then the record will be lost.
 * </p>
 *
 * <p>
 * <strong>acks=all</strong> or <strong>acks=-1</strong>
 * <br>
 * This means the leader will wait for the full set of in-sync replicas to acknowledge the record.
 * This guarantees that the record will not be lost as long as at least one in-sync replica remains alive.
 * This is the strongest available guarantee.
 * </p>
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.3
 */
public final class Acks extends KfAttrEnvelope {

  /**
   * Ctor.
   *
   * @param value The value.
   */
  public Acks(final String value) {
    super(value, "acks");
  }
}
