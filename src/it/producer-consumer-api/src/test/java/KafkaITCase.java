/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2024 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.cactoos.list.ListOf;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

/**
 * Kafka IT Case.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
public abstract class KafkaITCase {

  /**
   * Kafka Docker Container.
   */
  protected static final KafkaContainer KAFKA = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.3.0")
  )
    .withEnv("auto.create.topics.enable", "true")
    .withReuse(true)
    .withLogConsumer(
      new Slf4jLogConsumer(
        LoggerFactory.getLogger(
          "testcontainers.kafka"
        )
      )
    )
    .withEmbeddedZookeeper();
  /**
   * Bootstrap servers.
   */
  protected static String servers;

  @BeforeAll
  static void setup() {
    KafkaITCase.KAFKA.start();
    KafkaITCase.servers =
      KafkaITCase.KAFKA.getBootstrapServers().replace("PLAINTEXT://", "");
    KafkaITCase.KAFKA.setEnv(
      new ListOf<>(
        "KAFKA_ADVERTISED_LISTENERS="
          + KafkaITCase.KAFKA.getBootstrapServers(),
        "KAFKA_LISTENERS=LISTENER_PUBLIC://" +
          KafkaITCase.KAFKA.getContainerName() +
          ":29092,LISTENER_INTERNAL://" +
          KafkaITCase.KAFKA.getBootstrapServers(),
        "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=LISTENER_PUBLIC:PLAINTEXT,LISTENER_INTERNAL:PLAINTEXT",
        "KAFKA_INTER_BROKER_LISTENER_NAME=LISTENER_PUBLIC"
      )
    );
  }

  @AfterClass
  public static void tearDown() {
    KafkaITCase.KAFKA.stop();
  }
}
