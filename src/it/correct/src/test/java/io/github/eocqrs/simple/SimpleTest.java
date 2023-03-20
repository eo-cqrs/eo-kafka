package io.github.eocqrs.simple;

import io.github.eocqrs.kafka.Consumer;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.consumer.KfConsumer;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerParams;
import io.github.eocqrs.kafka.consumer.settings.KfConsumerSettings;
import io.github.eocqrs.kafka.data.KfData;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.settings.KfProducerParams;
import io.github.eocqrs.kafka.producer.settings.KfProducerSettings;
import io.github.eocqrs.kafka.settings.*;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@Testcontainers
final class SimpleTest {

  @Container
  public final KafkaContainer kafka =
    new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.0"))
      .withEmbeddedZookeeper();

  @BeforeEach
  void startKafka() {
    this.kafka.start();
  }

  @AfterEach
  void stopKafka() {
    this.kafka.stop();
  }

  @Test
  public void kafkaRunning() {
    MatcherAssert.assertThat(
      "Kafka container running",
      this.kafka.isRunning(),
      Matchers.is(true)
    );
  }

  @Test
  public void pollsDataCorrectly() {
    try (
      final Producer<String, String> producer =
        new KfProducer<>(
          new KfProducerSettings<>(
            new KfProducerParams(
              new KfParams(
                new BootstrapServers(this.kafka.getBootstrapServers()),
                new KeySerializer("org.apache.kafka.common.serialization.StringSerializer"),
                new ValueSerializer("org.apache.kafka.common.serialization.StringSerializer")
              )
            )
          )
        );
      final Consumer<String, String> consumer =
        new KfConsumer<>(
          new KfConsumerSettings<>(
            new KfConsumerParams(
              new KfParams(
                new BootstrapServers(this.kafka.getBootstrapServers()),
                new GroupId("1"),
                new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
                new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
              )
            )
          )
        )
    ) {
      consumer.subscribe(new ListOf<>("test-t"));
      producer.send(
        "my_key",
        new KfData<>("data-from-test", "test-t", 1)
      );
      MatcherAssert.assertThat(
        "Consumes data right",
        consumer.iterate("test-t", Duration.ofMillis(5000L)).size(),
        Matchers.equalTo(1)
      );
    } catch (final Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

}