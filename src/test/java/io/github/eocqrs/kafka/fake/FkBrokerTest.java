package io.github.eocqrs.kafka.fake;

import com.jcabi.xml.XML;
import org.junit.jupiter.api.Test;
import org.xembly.Directives;

import java.io.IOException;

/**
 * Test case for {@link FkBroker}
 *
 * @since 0.2.3
 */
class FkBrokerTest {

  @Test
  void createsBroker() throws Exception {
    FkBroker broker = new FkBroker.InFile();
    final FkBroker.Synced synced = new FkBroker.Synced(broker);
    final XML xml = synced.xml();
    System.out.println(xml);
    synced.apply(
      new Directives()
        .xpath("/broker")
        .addIf("test")
    );
    final XML after = synced.xml();
    System.out.println(after);
  }
}