package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ParamsAttr;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski ()
 * @since
 */
final class GroupInstanceIdTest {

  /**
   * Under test.
   */
  private ParamsAttr instance;

  @BeforeEach
  void setUp() {
    this.instance = new GroupInstanceId("1");
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.instance.name(),
      Matchers.equalTo("group.instance.id")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.instance.asXml(),
      Matchers.equalTo("<groupInstanceId>1</groupInstanceId>")
    );
  }
}
