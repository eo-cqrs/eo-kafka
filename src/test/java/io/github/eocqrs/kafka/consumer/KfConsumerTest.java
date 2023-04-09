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

import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.ConsumerSettings;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import io.github.eocqrs.kafka.xml.KfXmlFlexible;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumer}
 *
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
final class KfConsumerTest {

  @Test
  void subscribes(
    @Mock final ConsumerSettings<String, String> settingsMock,
    @Mock final KafkaConsumer<String, String> consumerMock
    ) {
    Mockito.when(settingsMock.consumer()).thenReturn(consumerMock);
    final Consumer<String, String> consumer = new KfConsumer<>(settingsMock);
    assertDoesNotThrow(
      () ->
        consumer.subscribe(
          new ListOf<>("transactions-info")
        )
    );
    assertDoesNotThrow(
      consumer::close
    );
  }

  @Test
  void constructsConsumerWithXML() throws Exception {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfXmlFlexible<String, String>("consumer.xml")
          .consumer()
      );
    assertThat(consumer).isNotNull();
  }

  @Test
  void constructsConsumerWithParams() {
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfFlexible<>(
          new KfConsumerParams(
            new KfParams(
              new BootstrapServers("localhost:9092"),
              new GroupId("1"),
              new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
              new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
            )
          )
        )
      );
    assertThat(consumer).isNotNull();
  }
}
