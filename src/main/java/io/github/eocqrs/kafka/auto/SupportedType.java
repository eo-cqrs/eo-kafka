package io.github.eocqrs.kafka.auto;

public enum SupportedType {
    YAML("yaml"), JSON("json"), XML("xml");

    private final String name;

    SupportedType(final String name) {
        this.name = name;
    }


    @Override
    public final String toString() {
        return this.name;
    }
}
