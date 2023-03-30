package io.github.eocqrs.kafka.parameters;

import io.github.eocqrs.kafka.ParamsAttr;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link PartitionAssignmentStrategy}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.0.3
 */
final class PartitionAssignmentStrategyTest {

  /**
   * Under test.
   */
  private ParamsAttr strategy;

  @BeforeEach
  void setUp() {
    this.strategy = new PartitionAssignmentStrategy(
      CooperativeStickyAssignor.class.getName()
    );
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.strategy.name(),
      Matchers.equalTo("partition.assignment.strategy")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.strategy.asXml(),
      Matchers.equalTo(
        "<partitionAssignmentStrategy>sticky</partitionAssignmentStrategy>"
      )
    );
  }
}