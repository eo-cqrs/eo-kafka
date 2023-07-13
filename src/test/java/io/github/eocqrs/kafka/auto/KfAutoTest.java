package io.github.eocqrs.kafka.auto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

final class KfAutoTest {

  @Test
  void createsAuto() {
    assertAll(() -> {
        for (int i = 0; i < 10; i++) {
          assertDoesNotThrow(
            () -> new KfAuto<String, String>(
              new AutoNames("xml")
            ).consumer()
          );
        }
      }

    );
  }
}
