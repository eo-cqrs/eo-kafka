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

package io.github.eocqrs.kafka.consumer;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import org.assertj.core.api.Assertions;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumerSettings}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("removal")
final class KfConsumerSettingsTest {

  @Test
  void constructsWithSettings() {
    try (
      final Consumer<String, String> consumer =
        new KfConsumer<>(
          new KfConsumerSettings<>(
            new XMLDocument(
              new File(
                "src/test/resources/consumer.xml"
              )
            )
          )
        )
    ) {
      Assertions.assertThat(consumer).isNotNull();
    } catch (final Exception ignored) { }
  }

  @Test
  void constructsWithResourceOf() {
    assertDoesNotThrow(
      () -> {
        final ConsumerSettings<String, String> settings =
          new KfConsumerSettings<>(new ResourceOf("consumer.xml"));
        Assertions.assertThat(settings.consumer()).isNotNull();
      }
    );
  }

  @Test
  void constructsWithString() {
    assertDoesNotThrow(
      () -> {
        final ConsumerSettings<String, String> settings = new KfConsumerSettings<>("consumer.xml");
        Assertions.assertThat(settings.consumer()).isNotNull();
      }
    );
  }

  @Test
  void constructsWithSettingsObject() {
    assertDoesNotThrow(
      () -> {
        final ConsumerSettings<String, String> settings =
          new KfFlexible<>(
            new KfConsumerParams(
              new KfParams(
                new BootstrapServers("localhost:9092"),
                new GroupId("1"),
                new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
                new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
              )
            )
          );
        Assertions.assertThat(settings.consumer()).isNotNull();
      }
    );
  }
}
