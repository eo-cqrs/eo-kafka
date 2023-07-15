package io.github.eocqrs.kafka.auto;

import io.github.eocqrs.kafka.KfTypified;
import lombok.SneakyThrows;
import org.cactoos.Scalar;

import java.util.Collections;
import java.util.Map;

public abstract class KfTypifiedEnvelope implements KfTypified {

    private final Scalar<Map<String, Object>> origin;
    private final SupportedType type;

    protected KfTypifiedEnvelope(
        final Scalar<Map<String, Object>> origin,
        final SupportedType type
    ) {
        this.origin = origin;
        this.type = type;
    }

    @Override
    public final SupportedType asType() {
        return this.type;
    }

    @SneakyThrows
    @Override
    public final Map<String, Object> value() {
        return Collections
            .unmodifiableMap(this.origin.value());
    }
}
