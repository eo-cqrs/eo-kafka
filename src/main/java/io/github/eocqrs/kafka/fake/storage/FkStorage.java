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

package io.github.eocqrs.kafka.fake.storage;

import com.jcabi.xml.XML;
import org.xembly.Directive;

/**
 * Fake Kafka Storage.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 * @deprecated use {@link io.github.eocqrs.xfake.FkStorage}
 */
@Deprecated
public interface FkStorage {

  /**
   * Full XML.
   *
   * @return XML
   * @throws Exception When something went wrong.
   */
  XML xml() throws Exception;

  /**
   * Update XML with new directives.
   *
   * @param dirs Directives
   * @throws Exception When something went wrong.
   */
  void apply(Iterable<Directive> dirs) throws Exception;

  /**
   * Locks storage to the current thread.
   *
   * <p>If the lock is available, grant it
   * to the calling thread and block all operations from other threads.
   * If not available, wait for the holder of the lock to release it with
   * {@link #unlock()} before any other operations can be performed.
   *
   * <p>Locking behavior is reentrant, which means a thread can invoke
   * multiple times, where a hold count is maintained.
   */
  void lock();

  /**
   * Unlock storage.
   *
   * <p>Locking behavior is reentrant, thus if the thread invoked
   * {@link #lock()} multiple times, the hold count is decremented. If the
   * hold count reaches 0, the lock is released.
   *
   * <p>If the current thread does not hold the lock, an
   * {@link IllegalMonitorStateException} will be thrown.
   */
  void unlock();
}
