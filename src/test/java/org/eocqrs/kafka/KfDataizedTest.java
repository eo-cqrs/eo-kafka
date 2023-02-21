package org.eocqrs.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
class KfDataizedTest {

  @Test
  void intDataize() {
    final int data = 13491;
    final Dataized<Integer> dataized = new KfDataized<>(data);
    assertThat(dataized.dataize()).isEqualTo(data);
  }

  @Test
  void stringDataize() {
    final String data = "data";
    final Dataized<String> dataized = new KfDataized<>(data);
    assertThat(dataized.dataize()).isEqualTo(data);
  }

  @Test
  void dataizedData() {
    final String customer = "customer-1";
    final Data<String> data = new KfData<>(
      "customer-1", "customers", 5
    );
    assertThat(data.dataized().dataize())
      .isEqualTo(customer);
  }
}