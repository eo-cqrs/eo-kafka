package io.github.eocqrs.kafka.auto;

import lombok.SneakyThrows;
import org.cactoos.Scalar;
import org.cactoos.io.Directory;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.Upper;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AutoNames implements Scalar<Map<Path, SupportedType>> {

  private final String directory;

  public AutoNames(final String directory) {
    this.directory = directory;
  }

  @SneakyThrows
  @Override
  public Map<Path, SupportedType> value() {
    return new ListOf<>(
      new Filtered<>(
        this::producerOrConsumerFile,
        new IterableOf<>(
          new Directory(
            new File(
              Objects.requireNonNull(
                Thread.currentThread()
                  .getContextClassLoader()
                  .getResource("")
              ).toURI()
            )
          ).iterator()
        )
      )
    ).stream()
      .collect(
        Collectors.toMap(
          path -> path,
          path ->
            SupportedType.valueOf(
              new Upper(
                new ExtensionOf(path).value()
              ).toString()
            )
        )
      );
  }

  private boolean producerOrConsumerFile(final Path path) {
    return (path.getFileName().toString().contains("producer")
      || path.getFileName().toString().contains("consumer"))
      && path.toFile().isFile()
      && path.getParent().endsWith(this.directory);
  }
}
