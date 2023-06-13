package io.github.eocqrs.kafka.fake;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TopicDirs}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
final class TopicDirsTest {

  @Test
  void dirsInRightFormat() throws Exception {
    MatcherAssert.assertThat(
      "Directives in the right format",
      new TopicDirs("test")
        .value()
        .toString(),
      Matchers.equalTo(
        "XPATH \"broker/topics\";ADD \"topic\";ADDIF \"name\";SET \"test\";UP;ADDIF \"datasets\";"
      )
    );
  }
}
