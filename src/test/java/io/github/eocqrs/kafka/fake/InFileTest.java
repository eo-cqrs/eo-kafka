package io.github.eocqrs.kafka.fake;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;

/**
 * Test case for {@link InFile}
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
final class InFileTest {

  @Test
  void createsStorageInXmlFile() throws Exception {
    final FkStorage storage = new InFile();
    MatcherAssert.assertThat(
      "Broker has root <broker> tag",
      storage.xml().nodes("broker").isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void appliesDirectives() throws Exception {
    final FkStorage storage = new InFile();
    storage.apply(
      new Directives()
        .xpath("/broker")
        .addIf("servers")
    );
    MatcherAssert.assertThat(
      "XML has right format",
      storage.xml().nodes("broker/servers").isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void locksAndUnlocks() throws Exception {
    final FkStorage storage = new InFile();
    Assertions.assertDoesNotThrow(
      storage::lock
    );
    Assertions.assertDoesNotThrow(
      storage::unlock
    );
  }
}