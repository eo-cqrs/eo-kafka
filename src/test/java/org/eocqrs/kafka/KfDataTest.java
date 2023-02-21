package org.eocqrs.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.1
 */
class KfDataTest {

  @Test
  void stringDataized() {
    final String origin = "test";
    final Data<String> data = new KfData<>(
      origin, "processes", 3
    );
    assertDoesNotThrow(data::dataized);
    assertThat(data.dataized()).isNotNull();
  }

  @Test
  void topic() {
    final String topic = "tx-log";
    final Data<String> data = new KfData<>(
      "test", topic, 12
    );
    assertThat(data.topic()).isEqualTo(topic);
  }

  @Test
  void partition() {
    final int partition = 1;
    final Data<String> data = new KfData<>(
      "test", "customer-messages", partition
    );
    assertThat(data.partition()).isEqualTo(partition);
  }
}