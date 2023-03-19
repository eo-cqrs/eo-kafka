package io.github.eocqrs.kafka.settings;

import io.github.eocqrs.kafka.SettingsAttribute;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class GroupIdTest {

  private SettingsAttribute id;

  @BeforeEach
  void setUp() {
    this.id = new GroupId("gid");
  }

  @Test
  void writesRightName() {
    MatcherAssert.assertThat(
      "Name in right format",
      this.id.name(),
      Matchers.equalTo("group.id")
    );
  }

  @Test
  void writesRightXml() {
    MatcherAssert.assertThat(
      "XML in right format",
      this.id.asXml(),
      Matchers.equalTo("<groupId>gid</groupId>")
    );
  }
}