package io.github.eocqrs.simple;

import com.jcabi.xml.XMLDocument;
import io.github.eocqrs.kafka.Producer;
import io.github.eocqrs.kafka.producer.KfProducer;
import io.github.eocqrs.kafka.producer.KfProducerSettings;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

final class SimpleTest {

  @Test
  void pollsDataCorrectly() {
    try (
      final Producer<String, String> producer =
        new KfProducer<>(
          new KfProducerSettings<String, String>(
            new XMLDocument(
              new ResourceOf("settings.xml").stream()
            )
          )
        );
    ) {
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}