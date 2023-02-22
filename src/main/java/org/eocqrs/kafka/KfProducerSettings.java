package org.eocqrs.kafka;

import com.jcabi.xml.XML;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.eocqrs.kafka.xml.TextXpath;

/**
 * @author Aliaksei Bialiauski (abialiauski@solvd.com)
 * @since 1.0
 */
@RequiredArgsConstructor
public final class KfProducerSettings<K, X> implements ProducerSettings<K, X> {

  private final XML xml;

  /**
   * @todo #26:20m/DEV test producer construction
   */
  @Override
  public KafkaProducer<K, X> producer() {
    final Map<String, Object> config = new HashMap<>(3);
    config.put("bootstrap.servers",
      new TextXpath(this.xml, "//bootstrapServers")
        .toString()
    );
    config.put("key.serializer",
      new TextXpath(this.xml, "//keySerializer")
        .toString()
    );
    config.put("value.serializer",
      new TextXpath(this.xml, "//valueSerializer")
        .toString()
    );
    return new KafkaProducer<>(config);
  }
}