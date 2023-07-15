package io.github.eocqrs.kafka.auto;

import org.cactoos.Scalar;

import java.util.Map;

/**
 * @todo #416 Default params when passed is empty
 */
public class KfTypifiedYamlParams extends KfTypifiedEnvelope {

    public KfTypifiedYamlParams(final Scalar<Map<String, Object>> params) {
        super(params, SupportedType.YAML);
    }
}
