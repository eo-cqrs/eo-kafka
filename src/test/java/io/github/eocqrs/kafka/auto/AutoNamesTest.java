package io.github.eocqrs.kafka.auto;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

final class AutoNamesTest {

    @Test
    void scansDirectories() {
        Stream.of("xml", "yaml")
            .map(AutoNames::new)
            .forEach(
                names ->
                    MatcherAssert.assertThat(
                        "'%s'\n\tShould have two files inside"
                            .formatted(names.value()),
                        names.value().keySet(),
                        Matchers.hasSize(2)
                    )
            );
    }
}
