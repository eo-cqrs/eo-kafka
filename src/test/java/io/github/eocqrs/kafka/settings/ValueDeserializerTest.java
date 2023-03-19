package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.ParamsAttr;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ValueDeserializer}.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
final class ValueDeserializerTest {
  /**
   * Under test.
   */
  private ParamsAttr key;

  @BeforeEach
  void setUp() {
    this.key = new ValueDeserializer("vd");
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.key.name(),
      Matchers.equalTo("value.deserializer")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.key.asXml(),
      Matchers.equalTo("<valueDeserializer>vd</valueDeserializer>")
    );
  }
}
