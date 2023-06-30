<img alt="logo" src="logo.svg" height="100px" />

This nice logo made by [@l3r8yJ](https://github.com/l3r8yJ)

[![Managed By Self XDSD](https://self-xdsd.com/b/mbself.svg)](https://self-xdsd.com/p/eo-cqrs/eo-kafka?provider=github)

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/eo-cars/eo-kafka)](https://www.rultor.com/p/eo-cqrs/eo-kafka)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
<br>

[![mvn](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml/badge.svg)](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml)
[![maven central](http://maven-badges.herokuapp.com/maven-central/io.github.eo-cqrs/eo-kafka/badge.svg)](https://search.maven.org/artifact/io.github.eo-cqrs/eo-kafka)
[![javadoc](https://javadoc.io/badge2/io.github.eo-cqrs/eo-kafka/javadoc.svg)](https://javadoc.io/doc/io.github.eo-cqrs/eo-kafka)
[![codecov](https://codecov.io/gh/eo-cqrs/eo-kafka/branch/master/graph/badge.svg?token=4IFT0H3Y01)](https://codecov.io/gh/eo-cqrs/eo-kafka)

[![Hits-of-Code](https://hitsofcode.com/github/eo-cqrs/eo-kafka)](https://hitsofcode.com/view/github/eo-cqrs/eo-kafka)
[![Lines-of-Code](https://tokei.rs/b1/github/eo-cqrs/eo-kafka)](https://github.com/eo-cqrs/eo-kafka)
[![PDD status](http://www.0pdd.com/svg?name=eo-cqrs/eo-kafka)](http://www.0pdd.com/p?name=eo-cqrs/eo-kafka)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/eo-cqrs/eo-kafka/blob/master/LICENSE.txt)

Project architect: [@h1alexbel](https://github.com/h1alexbel)

EO Kafka Producers and consumers for working with Apache Kafka message broker.

Read [_Kafka Producers and Consumers for Elegant Microservices_](https://h1alexbel.github.io/2023/03/26/eo-kafka-for-elegant-microservices.html), the blog post about `EO-Kafka`
<br>
and [_EO-Kafka with Spring_](https://h1alexbel.github.io/2023/04/15/eo-kafka-with-spring.html), how to connect `EO-Kafka` with Spring.

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

To use it with [Spring Boot](https://spring.io/):
```xml
<dependency>
  <groupId>io.github.eo-cqrs</groupId>
  <artifactId>eo-kafka</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

Gradle:
```groovy
dependencies {
    compile 'io.github.eo-cqrs:eo-kafka:<version>'
}
```

## Messages API
To create Kafka Message with **Topic**, **Key** and **Value**:
```java
final Message<String, String> msg = new Tkv<>("test.topic", "test-k", "test-v");
```

Creation Kafka Message with **Partition**:
```java
final Message<String, String> msg = 
  new WithPartition<>(
    0,
    new Tkv<>(
      "test.topic",
      "test-k",
      "test-v"
    )
  );
```

Creation Kafka Message with **Timestamp**:
```java
final Message<String, String> msg =
  new Timestamped<>(
      tmstmp,
      new WithPartition<>(
        partition,
        new Tkv<>(
          topic,
          key,
          value
        )
    )
);
```

## Producer API
To create Kafka Producer you can wrap original [KafkaProducer](https://kafka.apache.org/23/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html):
```java
final KafkaProducer origin = ...;
final Producer<String, String> producer = new KfProducer<>(origin);
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
        new WithPartition<>(
          0,
          new Tkv<>(
            "xyz.topic",
            "key",
            "message"
        )
      )
    );
    } catch (Exception e) {
        throw new IllegalStateException(e);
  }
}
```

Also, you can create [KfCallback](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/producer/KfCallback.java), Kafka Producer with Async [Callback](https://kafka.apache.org/26/javadoc/org/apache/kafka/clients/producer/Callback.html) support:
```java
final Producer<String, String> producer =
  new KfCallback<>(
    new KfFlexible<>(
      new KfProducerParams(
        new KfParams(
          // producer params
        )
      )
    ),
    new Callback() {
      @Override
      public void onCompletion(final RecordMetadata meta, final Exception ex) {
        // logic
      }
    }
);    
```

## Consumer API
To create Kafka Consumer you can wrap original [KafkaConsumer](https://kafka.apache.org/23/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html):
```java
final KafkaConsumer origin = ...;
final Consumer<String, String> producer = new KfConsumer<>(origin);
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
//    consumer.subscribe(new ListOf<>("orders-saga-init")));
//    or call #records(topic, duration) it will subscribe to the topic you provide
      final ConsumerRecords<String, String> records = consumer.records("orders-saga-init", Duration.ofSeconds(5L));
    }
  }
```
Also, you can `subscribe` with [ConsumerRebalanceListener](https://kafka.apache.org/24/javadoc/index.html?org/apache/kafka/clients/consumer/ConsumerRebalanceListener.html):
```java
consumer.subscribe(new ConsumerRebalanceListener() {
    @Override
    public void onPartitionsRevoked(final Collection<TopicPartition> partitions) {
    }
    @Override
    public void onPartitionsAssigned(final Collection<TopicPartition> partitions) {
    }
  }, "<your topic>");
 }
);
```

Finally, you can `unsubscribe`:
```java
consumer.unsubscribe();
```

## Fake API

In case of mocking eo-kafka, you can use existing Fake Objects from `io.github.eocqrs.kafka.fake` package.
They look like a normal ones, but instead of talking to real Kafka broker,
they are manipulating in-memory XML document.

### FkBroker

```java
final FkBroker broker = new InXml(
   new Synchronized(
     new InFile(
       "consumer-test", "<broker/>"
     )
   )
);
```

It will create in-memory XML document with following structure:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<broker>
  <topics/>
  <subs/>
</broker>
```

you can create a topic inside broker:

```java
broker.with(new TopicDirs("fake.topic").value());
```

Under the hood XML will be modified to:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<broker>
  <topics>
    <topic>
      <name>fake.topic</name>
      <datasets/>
    </topic>
  </topics>
  <subs/>
</broker>
```

### FkProducer

```java
final Producer<String, String> producer =
    new FkProducer<>(
      UUID.randomUUID(),
      broker
);
```

### FkConsumer

```java
final Consumer<Object, String> consumer =
    new FkConsumer(
      UUID.randomUUID(),
      broker
);
```

### Fake API Example

```java
final String topic = "test";
final Consumer<Object, String> consumer =
   new FkConsumer(UUID.randomUUID(),
     this.broker
       .with(new TopicDirs(topic).value())
   );
final Producer<String, String> producer =
   new FkProducer<>(UUID.randomUUID(), this.broker);
producer.send(
  new WithPartition<>(
      0,
      new Tkv<>(
        topic,
        "test1",
        "test-data-1"
      )
    )
);
producer.send(
  new WithPartition<>(
      0,
      new Tkv<>(
        topic,
        "test2",
        "test-data-2"
      )
    )
);
producer.send(
  new WithPartition<>(
      0,
      new Tkv<>(
        topic,
        "test-data-3",
        "test3"
      )
    )
);
final ConsumerRecords<Object, String> records =
   consumer.records(topic, Duration.ofSeconds(1L));
final List<String> datasets = new ListOf<>();
records.forEach(rec -> datasets.add(rec.value()));
MatcherAssert.assertThat(
   "First datasets in right format",
   datasets,
   Matchers.contains("test-data-1", "test-data-2", "test-data-3")
);
```

As well as production producers and consumers, fake ones also should be closed after things been done:

```java
fake.close();
```

Under the hood XML document will looks like this:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<broker>
  <topics>
    <topic>
      <name>test</name>
      <datasets>
        <dataset>
          <partition>0</partition>
          <key>test1</key>
          <value>test-data-1</value>
          <seen>true</seen>
        </dataset>
        <dataset>
          <partition>0</partition>
          <key>test2</key>
          <value>test-data-2</value>
          <seen>true</seen>
        </dataset>
        <dataset>
          <partition>0</partition>
          <key>test3</key>
          <value>test-data-3</value>
          <seen>true</seen>
        </dataset>
      </datasets>
    </topic>
  </topics>
  <subs>
    <sub>
      <topic>test</topic>
      <consumer>aa4a2008-764b-4e19-9368-8250df4bea38</consumer>
    </sub>
  </subs>
</broker>
```

**By the version `0.3.5`, eo-kafka support only String values in FkConsumer**.

## Config API
| Kafka Property                          | eo-kafka API                                                                                                                                                    | XML tag
|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------| ----------------------
| `bootstrap.servers`                     | [BootstrapServers](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BootstrapServers.java)                       | bootstrapServers
| `key.serializer`                        | [KeySerializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KeySerializer.java)                             | keySerializer
| `value.serializer`                      | [ValueSerializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ValueSerializer.java)                         | valueSerializer
| `key.deserializer`                      | [KeyDeserializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/KeyDeserializer.java)                         | keyDeserializer
| `value.deserializer`                    | [ValueDeserializer](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ValueDeserializer.java)                     | valueDeserializer
| `group.id`                              | [GroupId](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/GroupId.java)                                         | groupId
| `auto.offset.reset`                     | [AutoOffsetReset](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/AutoOffsetReset.java)                         | autoOffsetReset
 | `client.id`                             | [ClientId](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ClientId.java)                                       | clientId
 | `client.rack`                           | [ClientRack](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ClientRack.java)                                   | clientRack
| `acks`                                  | [Acks](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/Acks.java)                                               | acks
| `security.protocol`                     | [SecurityProtocol](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SecurityProtocol.java)                       | securityProtocol
| `sasl.jaas.config`                      | [SaslJaasConfig](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SaslJaasConfig.java)                           | saslJaasConfig
| `sasl.mechanism`                        | [SaslMechanism](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SaslMechanism.java)                             | saslMechanism
| `batch.size`                            | [BatchSize](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BatchSize.java)                                     | batchSize
| `buffer.memory`                         | [BufferMemory](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/BufferMemory.java)                               | bufferMemory
| `linger.ms`                             | [LingerMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/LingerMs.java)                                       | lingerMs
| `retries`                               | [Retries](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/Retries.java)                                         | retries
| `retry.backoff.ms`                      | [RetryBackoffMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/RetryBackoffMs.java)                           | retryBackoffMs
| `compression.type`                      | [CompressionType](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/CompressionType.java)                         | compressionType
| `partition.assignment.strategy`         | [PartitionAssignmentStrategy](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/PartitionAssignmentStrategy.java) | partitionAssignmentStrategy
| `max.poll.records`                      | [MaxPollRecords](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxPollRecords.java)                           | maxPollRecords
| `max.poll.interval.ms`                  | [MaxPollIntervalMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxPollIntervalMs.java)                        | maxPollIntervalMs
| `heartbeat.interval.ms`                 | [HeartbeatIntervalMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/HeartbeatIntervalMs.java)                 | heartbeatIntervalMs
| `enable.auto.commit`                    | [EnableAutoCommit](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/EnableAutoCommit.java)                       | enableAutoCommit
| `session.timeout.ms`                    | [SessionTimeoutMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SessionTimeoutMs.java)                       | sessionTimeoutMs
| `max.partition.fetch.bytes`             | [MaxPartitionFetchBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxPartitionFetchBytes.java)           | maxPartitionFetchBytes
| `fetch.max.wait.ms`                     | [FetchMaxWaitMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/FetchMaxWaitMs.java)                           | fetchMaxWaitMs
| `fetch.min.bytes`                       | [FetchMinBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/FetchMinBytes.java)                             | fetchMinBytes
| `fetch.max.bytes`                       | [FetchMaxBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/FetchMaxBytes.java)                             | fetchMaxBytes
| `send.buffer.bytes`                     | [SendBufferBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/SendBufferBytes.java)                         | sendBufferBytes
| `receive.buffer.bytes`                  | [ReceiveBufferBytes](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/ReceiveBufferBytes.java)                   | receiveBufferBytes
| `max.block.ms`                          | [MaxBlockMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxBlockMs.java)                                   | maxBlockMs
| `max.request.size`                      | [MaxRqSize](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxRqSize.java)                                     | maxRequestSize
| `group.instance.id`                     | [GroupInstanceId](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/GroupInstanceId.java)                         | groupInstanceId
| `max.in.flight.requests.per.connection` | [MaxInFlightRq](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/MaxInFlightRq.java)                             | maxInFlightRequestsPerConnection
| `delivery.timeout.ms`                   | [DeliveryTimeoutMs](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/DeliveryTimeoutMs.java)                     | deliveryTimeoutMs
| `enable.idempotence`                    | [EnableIdempotence](https://github.com/eo-cqrs/eo-kafka/blob/master/src/main/java/io/github/eocqrs/kafka/parameters/EnableIdempotence.java)                     | enableIdempotence

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