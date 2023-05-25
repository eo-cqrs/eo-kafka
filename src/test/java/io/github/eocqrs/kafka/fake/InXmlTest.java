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

package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.xfake.FkStorage;
import io.github.eocqrs.xfake.InFile;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test case for {@link  InXml}
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
@ExtendWith(MockitoExtension.class)
final class InXmlTest {

  /**
   * Storage.
   */
  private FkStorage storage;

  @BeforeEach
  void setUp() throws Exception {
    this.storage = new InFile("fake-kafka", "<broker/>");
  }

  @Test
  void createsBroker(@Mock final FkStorage mock) throws Exception {
    final FkBroker broker = new InXml(mock);
    MatcherAssert.assertThat(
      "Broker is not null",
      broker,
      Matchers.notNullValue()
    );
  }

  @Test
  void createsBrokerWithStorage() throws Exception {
    final FkBroker broker =
      new InXml(
        this.storage
      );
    MatcherAssert.assertThat(
      "<topics> node is appended",
      this.storage.xml()
        .nodes("broker/topics")
        .isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void createsTopic() throws Exception {
    new InXml(this.storage)
      .withTopics("test-1");
    MatcherAssert.assertThat(
      "Topic is present in XML",
      this.storage.xml()
        .nodes("broker/topics/topic[name = 'test-1']")
        .isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void createsMultipleTopics() throws Exception {
    new InXml(this.storage)
      .withTopics("test-1", "test-2", "test-3");
    MatcherAssert.assertThat(
      "Topic is present in XML",
      this.storage.xml()
        .nodes("broker/topics/topic[name = 'test-1']")
        .isEmpty(),
      Matchers.equalTo(false)
    );
    MatcherAssert.assertThat(
      "Topic is present in XML",
      this.storage.xml()
        .nodes("broker/topics/topic[name = 'test-2']")
        .isEmpty(),
      Matchers.equalTo(false)
    );
    MatcherAssert.assertThat(
      "Topic is present in XML",
      this.storage.xml()
        .nodes("broker/topics/topic[name = 'test-3']")
        .isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void addsDataset() throws Exception {
    final String topic = "test-1";
    final String value = "value";
    new InXml(this.storage)
      .withTopics(topic)
      .withDataset(
        "test",
        new KfData<>(
          value,
          topic,
          0
        )
      );
    MatcherAssert.assertThat(
      "Dataset is present",
      this.storage.xml()
        .nodes(
          "broker/topics/topic[name = '%s']/datasets/dataset[value = '%s']"
            .formatted(
              topic,
              value
            )
        )
        .isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void queriesEmptyData() throws Exception {
    MatcherAssert.assertThat(
      "Broker queries empty data and gets right response",
      new InXml(this.storage)
        .data("broker/topics/value"),
      Matchers.empty()
    );
  }

  @Test
  void queriesExistingData() throws Exception {
    final String topic = "temp";
    final FkBroker broker = new InXml(
      this.storage
    ).withTopics(topic);
    MatcherAssert.assertThat(
      "Broker queries data and gets right response",
      broker.data(
        "broker/topics/topic[name = '%s']/name/text()"
          .formatted(
            topic
          )
      ),
      Matchers.contains("temp")
    );
  }
}
