package io.github.eocqrs.kafka.auto;

import org.cactoos.Scalar;

import java.util.Map;

public class KfTypifiedXmlParams extends KfTypifiedEnvelope {

    public KfTypifiedXmlParams(final Scalar<Map<String, Object>> params) {
        super(params, SupportedType.XML);
    }
}
