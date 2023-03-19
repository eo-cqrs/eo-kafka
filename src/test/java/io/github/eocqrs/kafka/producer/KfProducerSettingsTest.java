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

package io.github.eocqrs.kafka.producer;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import io.github.eocqrs.kafka.producer.settings.KfProducerSettings;
import io.github.eocqrs.kafka.settings.BootstrapServers;
import io.github.eocqrs.kafka.settings.KeySerializer;
import io.github.eocqrs.kafka.settings.KfParams;
import io.github.eocqrs.kafka.settings.ValueSerializer;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfProducerSettings}
 *
 * @since 0.0.0
 */
final class KfProducerSettingsTest {

  @Test
  void constructsWithSettings() throws FileNotFoundException {
    final ProducerSettings<String, String> settings =
      new KfProducerSettings<>(
        new XMLDocument(
          new File("src/test/resources/settings.xml")
        )
      );
    assertThat(settings.producer()).isNotNull();
  }

  @Test
  void constructsWithResourceOf() {
    assertDoesNotThrow(
      () -> {
        final ProducerSettings<String, String> settings =
          new KfProducerSettings<>(new ResourceOf("settings.xml"));
        assertThat(settings.producer()).isNotNull();
      }
    );
  }

  @Test
  void constructsWithString() {
    assertDoesNotThrow(
      () -> {
        final ProducerSettings<String, String> settings = new KfProducerSettings<>("settings.xml");
        assertThat(settings.producer()).isNotNull();
      }
    );
  }

  @Test
  void constructsWithSettingsObject() {
    assertDoesNotThrow(
      () -> {
        final ProducerSettings<String, String> settings =
          new KfProducerSettings<>(
            new KfProducerParams(
              new KfParams(
                new ValueSerializer("org.apache.kafka.common.serialization.StringSerializer"),
                new KeySerializer("org.apache.kafka.common.serialization.StringSerializer"),
                new BootstrapServers("localhost:9092")
              )
            )
          );
        assertThat(settings.producer()).isNotNull();
      }
    );
  }
}
