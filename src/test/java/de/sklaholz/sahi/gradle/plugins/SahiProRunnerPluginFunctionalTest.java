package de.sklaholz.sahi.gradle.plugins;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import de.sklaholz.sahi.gradle.Constants.Tasks;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SahiProRunnerPluginFunctionalTest {

  @TempDir
  File projectDir;

  private File getBuildFile() {
    return new File(projectDir, "build.gradle.kts");
  }

  private File getSettingsFile() {
    return new File(projectDir, "settings.gradle.kts");
  }

  @Disabled
  @Test
  void canRunTask() throws IOException {
    // given
    writeString(getSettingsFile(), "");
    writeString(getBuildFile(),
        """
            plugins {
                id("de.sklaholz.sahi-pro-runner")
            }

            tasks.runSahiPro {
                config.scriptName = "scenario/smoketest.s.csv"
                config.browser = "edgenew"
                config.baseURL = "http://example.com/"
                config.nodes = "master.localhost.local:9999"
                config.masterHost = "master.localhost.local"
                config.masterPort = "9999"
            }
            """);

    final var runner = createGradleRunner();

    // when
    final BuildResult result = runner.build();

    // then
    then(result.getOutput()).contains("Task :runSahiPro UP-TO-DATE");
  }

  @ParameterizedTest
  @ValueSource(strings = {"8.4", "8.5", "8.6", "8.7"})
  void verifyGradleVersion(final String givenGradleVersion) throws IOException {
    // given
    writeString(getSettingsFile(), "");
    writeString(getBuildFile(),
        """
            plugins {
                id("de.sklaholz.sahi-pro-runner")
            }

            tasks.runSahiPro {
                config.scriptName = "scenario/smoketest.s.csv"
                config.browser = "edgenew"
                config.baseURL = "http://example.com/"
                config.nodes = "master.localhost.local:9999"
                config.masterHost = "master.localhost.local"
                config.masterPort = "9999"
            }
            """);
    final var runner = createGradleRunner();
    runner.withGradleVersion(givenGradleVersion);

    // when
    final var actual = catchThrowable(runner::build);

    // then
    then(actual).hasMessageContaining("SahiPro Runner Plugin requires Gradle 8.8 and higher");
  }

  private GradleRunner createGradleRunner() {
    final GradleRunner runner = GradleRunner.create();
    runner.withProjectDir(projectDir);
    runner.withArguments(Tasks.SAHI_PRO_RUNNER_TASK_NAME);
    runner.withPluginClasspath();
    runner.forwardOutput();
    return runner;
  }

  private void writeString(final File file, final String string) throws IOException {
    try (final Writer writer = new FileWriter(file)) {
      writer.write(string);
    }
  }
}
