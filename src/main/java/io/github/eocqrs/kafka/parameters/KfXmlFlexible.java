package io.github.eocqrs.kafka.parameters;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @todo #154:30m/DEV Get rid of deprecated api.
 * We have to remove <b>all</b> deprecated api, before release `0.0.3`.
 */
/**
 * Allow creating custom Consumer/Producer from XML.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.2
 */
public final class KfXmlFlexible<K, X> extends KfFlexibleEnvelope<K, X> {

  /**
   * Ctor.
   *
   * @param name Name of XML configuration placed in resources folder.
   */
  public KfXmlFlexible(final String name) throws Exception {
    super(name);
  }

  @Override
  @SneakyThrows
  public KafkaConsumer<K, X> consumer() {
    return new KafkaConsumer<K, X>(
      new XmlMapParams(this.settings, KfCustomer.CONSUMER).value()
    );
  }

  @Override
  @SneakyThrows
  public KafkaProducer<K, X> producer() {
    return new KafkaProducer<K, X>(
      new XmlMapParams(this.settings, KfCustomer.PRODUCER).value()
    );
  }

}
