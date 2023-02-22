package org.eocqrs.kafka.xml;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public final class TextXpath {

  private final XML xml;
  private final String node;

  @Override
  public String toString() {
    return this.xml.nodes(this.node)
      .get(0)
      .xpath("text()")
      .get(0);
  }
}