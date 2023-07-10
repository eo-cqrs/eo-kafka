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

package io.github.eocqrs.kafka.yaml;

import lombok.RequiredArgsConstructor;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.io.ResourceOf;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class converts a YAML source from
 * kebab case keys into kafka specific keys Map.
 */
@RequiredArgsConstructor
public final class YamlMapParams implements Scalar<Map<String, Object>> {

  /**
   * The config value.
   */
  private final Map<String, Object> value;

  /**
   * Filename ctor.
   *
   * @param name Config name
   * @throws Exception When something went wrong
   */
  public YamlMapParams(final String name) throws Exception {
    this(new ResourceOf(name));
  }

  /**
   * Input ctor.
   *
   * @param input Input source
   * @throws Exception When something went wrong
   */
  public YamlMapParams(final Input input) throws Exception {
    this(input.stream());
  }

  /**
   * Primary ctor.
   *
   * @param stream Input source
   */
  public YamlMapParams(final InputStream stream) {
    this.value = new Yaml().load(stream);
  }

  @Override
  public Map<String, Object> value() {
    final Map<String, Object> accum = new HashMap<>(0);
    this.value
      .forEach(
        (key, val) -> accum.put(
          key.replace('-', '.'),
          val
        )
      );
    return Collections.unmodifiableMap(accum);
  }
}
