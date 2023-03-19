package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.SettingsAttribute;
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
final class ValueSerializerTest {

  /**
   * Under test.
   */
  private SettingsAttribute key;

  @BeforeEach
  void setUp() {
    this.key = new ValueSerializer("vs");
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.key.name(),
      Matchers.equalTo("value.serializer")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.key.asXml(),
      Matchers.equalTo("<valueSerializer>vs</valueSerializer>")
    );
  }
}
