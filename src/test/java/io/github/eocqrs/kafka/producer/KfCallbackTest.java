/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Aliaksei Bialiauski, EO-CQRS
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

package io.github.eocqrs.kafka.producer;

import io.github.eocqrs.kafka.ProducerSettings;
import io.github.eocqrs.kafka.data.KfData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test case for {@link KfCallback}.
 *
 * @since 0.1.0
 */
@ExtendWith(MockitoExtension.class)
final class KfCallbackTest {

  @Test
  void sendsWithCallback(@Mock final KafkaProducer<String, String> origin) {
    try (
      final KfCallback<String, String> producer =
        new KfCallback<>(
          origin,
          (recordMetadata, e) ->
            MatcherAssert.assertThat(
              recordMetadata.topic(),
              Matchers.equalTo("fake-topic")
            )
        )
    ) {
      Assertions.assertDoesNotThrow(
        () -> producer.send(
          "fake-key",
          new KfData<>(
            "fake-data",
            "fake-topic",
            101
          )
        )
      );
    }
  }

  @Test
  void sendsWithCallbackViaSettings(
    @Mock final KafkaProducer<String, String> origin,
    @Mock final ProducerSettings<String, String> settings
  ) {
    Mockito.when(settings.producer()).thenReturn(origin);
    try (
      final KfCallback<String, String> producer =
        new KfCallback<>(
          settings,
          (recordMetadata, e) ->
            MatcherAssert.assertThat(
              recordMetadata.topic(),
              Matchers.equalTo("fake-topic")
            )
        )
    ) {
      Assertions.assertDoesNotThrow(
        () -> producer.send(
          "fake-key",
          new KfData<>(
            "fake-data",
            "fake-topic",
            101
          )
        )
      );
    }
  }
}
