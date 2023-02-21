package org.eocqrs.kafka;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public class KfProducerSettings implements ProducerSettings {

  private final XML xml;

  public static void main(String[] args) throws FileNotFoundException {
    final XMLDocument document = new XMLDocument(new File("producer.xml"));
    final ProducerSettings settings = new KfProducerSettings(
      document
    );
    final XML server = document.nodes("//valueSerializer").get(0);
    System.out.println(server.xpath("text()").get(0));
  }

  @Override
  public String bootstrapServers() {
    final XML item = this.xml.nodes("//bootstrapServers").get(0);
    return item.xpath("text()").get(0);
  }

  @Override
  public String keyClass() {
    final XML item = this.xml.nodes("//keySerializer").get(0);
    return item.xpath("text()").get(0);
  }

  @Override
  public String valueClass() {
    final XML item = this.xml.nodes("//valueSerializer").get(0);
    return item.xpath("text()").get(0);
  }
}