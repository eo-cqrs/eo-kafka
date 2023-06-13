package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.kafka.data.KfData;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link DatasetDirs}.
 *
 * @author Aliaksei Bialiauski (abialaiuski.dev@gmail.com)
 * @since 0.3.5
 */
final class DatasetDirsTest {

  @Test
  void dirsInRightFormat() throws Exception {
    final String directives = "XPATH \"broker/topics/topic[name = &apos;&apos;]\";ADDIF \"datasets\";ADD \"dataset\";ADD \"partition\";\n" +
      "4:SET \"0\";UP;ADDIF \"key\";SET \"test\";UP;ADDIF \"value\";SET \"\";";
    MatcherAssert.assertThat(
      "Directives in the right format",
      new DatasetDirs<>(
        "test",
        new KfData<>("", "", 0)
      ).value().toString(),
      Matchers.equalTo(
        directives
      )
    );
  }
}
