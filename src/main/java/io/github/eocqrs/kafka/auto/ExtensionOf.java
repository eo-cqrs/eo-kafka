package io.github.eocqrs.kafka.auto;

import org.cactoos.Scalar;

import java.nio.file.Path;

public final class ExtensionOf implements Scalar<String> {
    private final String filename;

    public ExtensionOf(final Path path) {
        this(path.toString());
    }

    public ExtensionOf(final String filename) {
        this.filename = filename;
    }

    @Override
    public String value() {
        return this.filename.substring(
            this.filename.lastIndexOf('.') + 1
        );
    }
}
