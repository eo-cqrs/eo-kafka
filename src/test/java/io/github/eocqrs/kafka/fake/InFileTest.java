package io.github.eocqrs.kafka.fake;

import com.jcabi.xml.XML;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
  void createsBrokerInXmlFile() throws Exception {
    final FkBroker broker = new InFile();
    MatcherAssert.assertThat(
      "Broker has root <broker> tag",
      broker.xml().nodes("broker").isEmpty(),
      Matchers.equalTo(false)
    );
  }

  @Test
  void appliesDirectives() throws Exception {
    final FkBroker broker = new InFile();
    broker.apply(
      new Directives()
        .xpath("/broker")
        .addIf("servers")
    );
    MatcherAssert.assertThat(
      "XML has right format",
      broker.xml().nodes("broker/servers").isEmpty(),
      Matchers.equalTo(false)
    );
  }
}