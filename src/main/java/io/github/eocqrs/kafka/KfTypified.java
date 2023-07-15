package io.github.eocqrs.kafka;

import io.github.eocqrs.kafka.auto.SupportedType;
import org.cactoos.Scalar;

import java.util.Map;

public interface KfTypified extends Scalar<Map<String, Object>> {

    SupportedType asType();

    @Override
    Map<String, Object> value();
}
