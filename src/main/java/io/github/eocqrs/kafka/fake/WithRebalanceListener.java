/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2024 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
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

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.cactoos.Scalar;
import org.xembly.Directives;

/**
 * Directives with Rebalance Listener.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @see org.apache.kafka.clients.consumer.ConsumerRebalanceListener
 * @since 0.3.5
 */
public final class WithRebalanceListener implements Scalar<Directives> {

  /**
   * Origin Directives.
   */
  private final Scalar<Directives> dirs;
  /**
   * ConsumerRebalanceListener.
   */
  private final ConsumerRebalanceListener listener;

  /**
   * Ctor.
   *
   * @param drs   Directives
   * @param lstnr Listener
   */
  public WithRebalanceListener(
    final Scalar<Directives> drs,
    final ConsumerRebalanceListener lstnr
  ) {
    this.dirs = drs;
    this.listener = lstnr;
  }

  @Override
  public Directives value() throws Exception {
    return this.dirs
      .value()
      .up()
      .addIf("listener")
      .set(this.listener.toString());
  }
}
