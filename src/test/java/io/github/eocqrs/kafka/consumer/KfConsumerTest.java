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
import io.github.eocqrs.kafka.Params;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.parameters.BootstrapServers;
import io.github.eocqrs.kafka.parameters.GroupId;
import io.github.eocqrs.kafka.parameters.KeyDeserializer;
import io.github.eocqrs.kafka.parameters.KfFlexible;
import io.github.eocqrs.kafka.parameters.KfParams;
import io.github.eocqrs.kafka.parameters.ValueDeserializer;
import io.github.eocqrs.kafka.xml.KfXmlFlexible;

import java.time.Duration;
import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.cactoos.io.ResourceOf;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test case for {@link KfConsumer}
 *
 * @since 0.0.0
 */
@ExtendWith(MockitoExtension.class)
final class KfConsumerTest {

  @Test
  void subscribesWithoutException(
    @Mock final ConsumerSettings<String, String> settings,
    @Mock final KafkaConsumer<String, String> consumer
  ) {
    Mockito.when(settings.consumer()).thenReturn(consumer);
    final Consumer<String, String> underTest = new KfConsumer<>(settings);
    assertAll(
      () -> assertDoesNotThrow(
        () -> {
          underTest.subscribe(new ListOf<>("transactions-info"));
          underTest.subscribe("transactions-info");
          underTest.subscribe(new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(final Collection<TopicPartition> partitions) {
            }

            @Override
            public void onPartitionsAssigned(final Collection<TopicPartition> partitions) {
            }
          }, "transactions-info");
        },
        "Should create an %s and subscribe without exceptions".formatted(underTest)
      ),
      () -> assertDoesNotThrow(
        underTest::close,
        () -> "Should close %s without an exception".formatted(underTest)
      )
    );
  }

  @Test
  void recordsPollingDoesntThrowException(
    @Mock final ConsumerSettings<String, String> settings,
    @Mock final KafkaConsumer<String, String> origin
  ) {
    Mockito.when(settings.consumer()).thenReturn(origin);
    assertDoesNotThrow(
      () ->
        new KfConsumer<>(settings).records(
          "TEST", Duration.ofSeconds(5L)
        ),
      () -> "Should to poll things without exception"
    );
  }

  @Test
  void unsubscribesWithoutException(
    @Mock final ConsumerSettings<String, String> settings,
    @Mock final KafkaConsumer<String, String> origin
  ) {
    Mockito.when(settings.consumer()).thenReturn(origin);
    final Consumer<String, String> consumer =
      new KfConsumer<>(settings);
    assertAll(
      () -> assertDoesNotThrow(consumer::unsubscribe),
      () -> assertDoesNotThrow(consumer::close),
      () -> "%s subscribes and unsubscribes without exceptions".formatted(consumer)
    );
  }

  @Test
  void constructsConsumerWithXML() throws Exception {
    final String xml = "consumer.xml";
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfXmlFlexible<String, String>(xml)
          .consumer()
      );
    assertThat(consumer).isNotNull()
      .describedAs(
        "%s should be created from %s without exceptions".formatted(
          consumer,
          new XMLDocument(
            new ResourceOf(xml).stream()
          )
        )
      );
  }

  @Test
  void constructsConsumerWithParams() {
    final Params params = new KfParams(
      new BootstrapServers("localhost:9092"),
      new GroupId("1"),
      new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
      new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
    );
    final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfFlexible<>(
          new KfConsumerParams(params)
        )
      );
    assertThat(consumer).isNotNull()
      .describedAs(
        "Consumer %s created from %s without exceptions".formatted(
          consumer,
          params.asXml()
        )
      );
  }
}
