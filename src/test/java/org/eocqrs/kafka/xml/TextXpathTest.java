package org.eocqrs.kafka.xml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
class TextXpathTest {

  @Test
  void testBootStrapServers() throws FileNotFoundException {
    final String servers = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/producer.xml")
      ),
      "//bootstrapServers"
    ).toString();
    assertThat(servers).isEqualTo("localhost");
  }

  @Test
  void testName() throws FileNotFoundException {
    final String name = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/test.xml")
      ),
      "//name"
    ).toString();
    assertThat(name).isEqualTo("h1alexbel");
  }

  @Test
  void testInvalidQuery() throws FileNotFoundException {
    final TextXpath xpath = new TextXpath(
      new XMLDocument(
        new File("src/test/resources/producer.xml")
      ),
      "//invalid"
    );
    assertThrows(Exception.class, xpath::toString);
  }
}