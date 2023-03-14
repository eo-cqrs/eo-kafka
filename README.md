<img alt="logo" src="logo.svg" height="100px" />

This nice logo made by [@l3r8yJ](https://github.com/l3r8yJ)

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](https://www.rultor.com/b/eo-cars/eo-kafka)](https://www.rultor.com/p/eo-cqrs/eo-kafka)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
<br>

[![PDD status](http://www.0pdd.com/svg?name=eo-cqrs/eo-kafka)](http://www.0pdd.com/p?name=eo-cqrs/eo-kafka)
[![mvn](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml/badge.svg)](https://github.com/eo-cqrs/eo-kafka/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/eo-cqrs/eo-kafka/branch/master/graph/badge.svg?token=4IFT0H3Y01)](https://codecov.io/gh/eo-cqrs/eo-kafka)
[![Hits-of-Code](https://hitsofcode.com/github/eo-cqrs/eo-kafka)](https://hitsofcode.com/view/github/eo-cqrs/eo-kafka)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/eo-cqrs/eo-kafka/blob/master/LICENSE)

Project architect: [@h1alexbel](https://github.com/h1alexbel)

EO Kafka Producers and consumers for working with Apache Kafka message broker.

**Motivation**. We are not happy with Spring Kafka, because it is very procedural and not object-oriented.
eo-kafka is suggesting to do almost exactly the same, but through objects.

**Principles**. These are the [design principles](https://www.elegantobjects.org/#principles) behind eo-kafka.

**How to use**. All you need is this (get the latest version here):

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
To create Kafka Producer Settings (Config):
```java
ProducerSettings<String, String> settings =
   new KfProducerSettings<>(
      new XMLDocument(
        new File("producer.xml") //xml file with all the settings
        )
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

To create Kafka Producer:
```java
Producer<String, String> producer =
  new KfProducer<>(
    new KfProducerSettings<String, String>(
      new XMLDocument(
        new File("producer.xml")
        )
    ).producer()
);
```

To send a [message](#messages):
```java
      try (
        final Producer<String, String> producer =
          new KfProducer<>(
            new KfProducerSettings<String, String>(
              new XMLDocument(
                new File("settings.xml")
              )
            ).producer()
          )
      ) {
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

To create Kafka Consumer Settings (Config):
```java
ConsumerSettings<String, String> settings =
   new KfConsumerSettings<>(
      new XMLDocument(
        new File("consumer.xml") //xml file with all the settings
        )
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

Creating Kafka Consumer:
TBD

Consuming messages:
TBD

## How to Contribute

Fork repository, make changes, send us a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install
```

You will need Maven 3.3+ and Java 17+.

Our [rultor image](https://github.com/eo-cqrs/eo-kafka-rultor-image) for CI/CD.
