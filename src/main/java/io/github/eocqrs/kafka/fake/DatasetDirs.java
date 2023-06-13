package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.kafka.Data;
import org.cactoos.Scalar;
import org.xembly.Directives;

/**
 * Dataset Directives.
 *
 * @param <K> Dataset key type
 * @param <X> Dataset data type
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
public final class DatasetDirs<K, X> implements Scalar<Directives> {

  /**
   * Dataset Key.
   */
  private final K key;
  /**
   * Dataset Data.
   */
  private final Data<X> data;

  /**
   * Ctor.
   *
   * @param key  Key
   * @param data Data
   */
  public DatasetDirs(final K key, final Data<X> data) {
    this.key = key;
    this.data = data;
  }

  @Override
  public Directives value() throws Exception {
    return new Directives()
      .xpath(
        "broker/topics/topic[name = '%s']"
          .formatted(
            this.data.topic()
          )
      )
      .addIf("datasets")
      .add("dataset")
      .add("partition")
      .set(this.data.partition())
      .up()
      .addIf("key")
      .set(this.key)
      .up()
      .addIf("value")
      .set(this.data.dataized().dataize());
  }
}
