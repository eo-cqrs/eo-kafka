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

package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.Settings;
import io.github.eocqrs.kafka.SettingsAttribute;
import org.cactoos.list.ListOf;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * It's a collection of `{@link SettingsAttribute}` objects.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public final class KfParams implements Settings {

  /**
   * The params.
   */
  private final Collection<SettingsAttribute> params;

  /**
   * Ctor.
   *
   * @param args Kafka parameters.
   */
  public KfParams(final SettingsAttribute... args) {
    this.params = new ListOf<>(args);
  }

  @Override
  public Collection<SettingsAttribute> all() {
    return Collections.unmodifiableCollection(this.params);
  }

  @Override
  public String asXml() {
    return this.params
      .stream()
      .map(SettingsAttribute::asXml)
      .collect(Collectors.joining("\n"));
  }
}
