<img alt="logo" src="logo.svg" height="100px" />

This nice logo made by [@l3r8yJ](https://github.com/l3r8yJ)

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/eo-cars/eo-kafka)](https://www.rultor.com/p/eo-cqrs/eo-kafka)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
<br>

[![mvn](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml/badge.svg)](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml)
[![maven central](http://maven-badges.herokuapp.com/maven-central/io.github.eo-cqrs/eo-kafka/badge.svg)](https://search.maven.org/artifact/io.github.eo-cqrs/eo-kafka)
[![codecov](https://codecov.io/gh/eo-cqrs/eo-kafka/branch/master/graph/badge.svg?token=4IFT0H3Y01)](https://codecov.io/gh/eo-cqrs/eo-kafka)

[![Hits-of-Code](https://hitsofcode.com/github/eo-cqrs/eo-kafka)](https://hitsofcode.com/view/github/eo-cqrs/eo-kafka)
[![Lines-of-Code](https://tokei.rs/b1/github/eo-cqrs/eo-kafka)](https://github.com/eo-cqrs/eo-kafka)
[![PDD status](http://www.0pdd.com/svg?name=eo-cqrs/eo-kafka)](http://www.0pdd.com/p?name=eo-cqrs/eo-kafka)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/eo-cqrs/eo-kafka/blob/master/LICENSE)

Project architect: [@h1alexbel](https://github.com/h1alexbel)

EO Kafka Producers and consumers for working with Apache Kafka message broker.

Read [_Kafka Producers and Consumers for Elegant Microservices_](https://h1alexbel.github.io/2023/03/26/eo-kafka-for-elegant-microservices.html), the blog post about `eo-kafka`.

**Motivation**. We are not happy with Spring Kafka, because it is very procedural and not object-oriented.
eo-kafka is suggesting to do almost exactly the same, but through objects.

**Principles**. These are the [design principles](https://www.elegantobjects.org/#principles) behind eo-kafka.

**How to use**. All you need is this (get the latest version [here](https://search.maven.org/artifact/io.github.eo-cqrs/eo-kafka)):

Maven:
```xml
<dependency>
  <groupId>io.github.eo-cqrs</groupId>
  <artifactId>eo-kafka</artifactId>
</dependency>
```

Gradle:
```groovy
dependencies {
    compile 'io.github.eo-cqrs:eo-kafka:<version>'
}
```

## Messages API
To create Kafka Message:
```java
Data<String> string =
  new KfData<>(
    "string-data",          //data
    "strings",              //topic
    1                       //partition
  );
```

## Producer API
To create Kafka Producer you can wrap original [KafkaProducer](https://kafka.apache.org/23/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html):
```java
KafkaProducer origin = ...;
Producer<String, String> producer = new KfProducer<>(origin);
```
Or construct it with [KfFlexible](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KfFlexible.java):
```java
final Producer<String, String> producer =
  new KfProducer<>(
    new KfFlexible<>(
      new KfProducerParams(
        new KfParams(
          new BootstrapServers("localhost:9092"),
          new KeySerializer("org.apache.kafka.common.serialization.StringSerializer"),
          new ValueSerializer("org.apache.kafka.common.serialization.StringSerializer")
        )
      )
    )
  );
```
Or create it with XML file:
```java
final Producer<String, String> producer =
  new KfProducer<>(
    new KfXmlFlexible<String, String>("producer.xml") // file with producer config
       .producer()
  );
```
btw, your [XML](https://en.wikipedia.org/wiki/XML#:~:text=Extensible%20Markup%20Language%20(XML)%20is,%2Dreadable%20and%20machine%2Dreadable.) file should be in the ```resources``` look like:
```xml
<producer>
  <bootstrapServers>localhost:9092</bootstrapServers>
  <keySerializer>org.apache.kafka.common.serialization.StringSerializer</keySerializer>
  <valueSerializer>org.apache.kafka.common.serialization.StringSerializer</valueSerializer>
</producer>
```

To send a [message](#messages-api):
```java
try (final Producer<String, String> producer = ...) {
      producer.send(
        "key2012",
        new KfData<>(
          "newRest28",
          "orders",
          1
        )
      );
    } catch (Exception e) {
        throw new IllegalStateException(e);
  }
}
```

## Consumer API
To create Kafka Consumer you can wrap original [KafkaConsumer](https://kafka.apache.org/23/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html):
```java
KafkaConsumer origin = ...;
Consumer<String, String> producer = new KfConsumer<>(origin);
```
Using [KfFlexible](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KfFlexible.java):
```java
final Consumer<String, String> consumer =
  new KfConsumer<>(
    new KfFlexible<>(
      new KfConsumerParams(
        new KfParams(
          new BootstrapServers("localhost:9092"),
          new GroupId("1"),
          new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
          new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
        )
      )
    )
  );
```

And XML File approach:
```java
final Consumer<String, String> consumer =
  new KfConsumer<>(
    new KfXmlFlexible<String, String>("consumer.xml")
      .consumer()
  );
```

Again, [XML](https://en.wikipedia.org/wiki/XML#:~:text=Extensible%20Markup%20Language%20(XML)%20is,%2Dreadable%20and%20machine%2Dreadable.) file should be in the ```resources``` look like:
```xml
<consumer>
  <bootstrapServers>localhost:9092</bootstrapServers>
  <groupId>1</groupId>
  <keyDeserializer>org.apache.kafka.common.serialization.StringDeserializer</keyDeserializer>
  <valueDeserializer>org.apache.kafka.common.serialization.StringDeserializer</valueDeserializer>
</consumer>
```

Consuming [messages](#messages-api):
```java
try (
  final Consumer<String, String> consumer =
      new KfConsumer<>(
        new KfFlexible<>(
          new KfConsumerParams(
            new KfParams(
              new BootstrapServers(this.severs),
              new GroupId("1"),
              new AutoOffsetReset("earliest"),
              new KeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer"),
              new ValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer")
            )
          )
        )
      )
  ) {
  // you need to be subscribed on a topic to iterate over data in the topic
      consumer.subscribe(new ListOf<>("orders-saga-init")));
      List<Dataized<String>> result = consumer.iterate("orders-saga-init", Duration.ofSeconds(5L));
    }
  }
```

## Config API
| Kafka Property                  | eo-kafka API                                                                                                                                                    | XML tag
|---------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------| ----------------------
| `bootstrap.servers`             | [BootstrapServers](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BootstrapServers.java)                       | bootstrapServers
| `key.serializer`                | [KeySerializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KeySerializer.java)                             | keySerializer
| `value.serializer`              | [ValueSerializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ValueSerializer.java)                         | valueSerializer
| `key.deserializer`              | [KeyDeserializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KeyDeserializer.java)                         | keyDeserializer
| `value.deserializer`            | [ValueDeserializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ValueDeserializer.java)                     | valueDeserializer
| `group.id`                      | [GroupId](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/GroupId.java)                                         | groupId
| `auto.offset.reset`             | [AutoOffsetReset](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/AutoOffsetReset.java)                         | autoOffsetReset
 | `client.id`                     | [ClientId](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ClientId.java)                                       | clientId
| `acks`                          | [Acks](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/Acks.java)                                               | acks
| `security.protocol`             | [SecurityProtocol](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SecurityProtocol.java)                       | securityProtocol
| `sasl.jaas.config`              | [SaslJaasConfig](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SaslJaasConfig.java)                           | saslJaasConfig
| `sasl.mechanism`                | [SaslMechanism](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SaslMechanism.java)                             | saslMechanism
| `batch.size`                    | [BatchSize](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BatchSize.java)                                     | batchSize
| `buffer.memory`                 | [BufferMemory](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BufferMemory.java)                               | bufferMemory
| `linger.ms`                     | [LingerMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/LingerMs.java)                                       | lingerMs
| `retries`                       | [Retries](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/Retries.java)                                         | retries
| `retry.backoff.ms`              | [RetryBackoffMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/RetryBackoffMs.java)                           | retryBackoffMs
| `compression.type`              | [CompressionType](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/CompressionType.java)                         | compressionType
| `partition.assignment.strategy` | [PartitionAssignmentStrategy](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/PartitionAssignmentStrategy.java) | partitionAssignmentStrategy
| `max.poll.records`              | [MaxPollRecords](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxPollRecords.java)                           | maxPollRecords
| `heartbeat.interval.ms`         | [HeartbeatIntervalMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/HeartbeatIntervalMs.java)                 | heartbeatIntervalMs
| `enable.auto.commit`            | [EnableAutoCommit](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/EnableAutoCommit.java)                       | enableAutoCommit
| `session.timeout.ms`            | [SessionTimeoutMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SessionTimeoutMs.java)                       | sessionTimeoutMs
| `max.partition.fetch.bytes`     | [MaxPartitionFetchBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxPartitionFetchBytes.java)           | maxPartitionFetchBytes
| `fetch.max.wait.ms`             | [FetchMaxWaitMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/FetchMaxWaitMs.java)                           | fetchMaxWaitMs
| `fetch.min.bytes`               | [FetchMinBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/FetchMinBytes.java)                             | fetchMinBytes
| `send.buffer.bytes`             | [SendBufferBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SendBufferBytes.java)                         | sendBufferBytes
| `receive.buffer.bytes`          | [ReceiveBufferBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ReceiveBufferBytes.java)                   | receiveBufferBytes
| `max.block.ms`                  | [MaxBlockMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxBlockMs.java)                            | maxBlockMs

## How to Contribute

Fork repository, make changes, send us a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install
```

You will need Maven 3.8.7+ and Java 17+.

If you want to contribute to the next release version of eo-kafka, please check the [project board](https://github.com/orgs/eo-cqrs/projects/2/views/1).

Our [rultor image](https://github.com/eo-cqrs/eo-kafka-rultor-image) for CI/CD.
