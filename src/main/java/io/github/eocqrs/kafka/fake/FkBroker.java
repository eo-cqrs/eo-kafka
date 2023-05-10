package io.github.eocqrs.kafka.fake;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.xembly.Directive;
import org.xembly.Xembler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.2.3
 */
public interface FkBroker {

  XML xml() throws Exception;

  void apply(Iterable<Directive> dirs) throws Exception;

  void lock();

  void unlock();

  final class InFile implements FkBroker {

    private final transient String name;

    public InFile() throws Exception {
      this(File.createTempFile("fake-kafka", ".xml"));
      new File(this.name).deleteOnExit();
    }

    public InFile(final File file) throws Exception {
      new LengthOf(
        new TeeInput(
          "<broker/>",
          file,
          StandardCharsets.UTF_8
        )
      ).value();
      this.name = file.getAbsolutePath();
    }

    @Override
    public XML xml() throws Exception {
      synchronized (this.name) {
        return new XMLDocument(
          new TextOf(
            new File(this.name)
          ).asString()
        );
      }
    }

    @Override
    public void apply(final Iterable<Directive> dirs) throws Exception {
      synchronized (this.name) {
        new LengthOf(
          new TeeInput(
            new XMLDocument(
              new Xembler(
                dirs
              ).applyQuietly(this.xml().node())
            ).toString(),
            new File(
              this.name
            ),
            StandardCharsets.UTF_8
          )
        ).value();
      }
    }

    @Override
    public void lock() {
      // nothing
    }

    @Override
    public void unlock() {
      // nothing
    }
  }

  final class Synced implements FkBroker {

    private final transient FkBroker origin;
    private final transient ImmutableReentrantLock lock =
      new ImmutableReentrantLock();

    public Synced(final FkBroker orgn) {
      this.origin = orgn;
    }

    @Override
    public XML xml() throws Exception {
      return this.origin.xml();
    }

    @Override
    public void apply(final Iterable<Directive> dirs) throws Exception {
      this.origin.apply(dirs);
    }

    @Override
    public void lock() {
      this.lock.lock();
    }

    @Override
    public void unlock() {
      this.lock.unlock();
    }
  }
}
