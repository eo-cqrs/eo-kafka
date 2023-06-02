package io.github.eocqrs.kafka.ext;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.5
 */
public interface Action {

  void apply();
}
