package de.sklaholz.sahi.gradle.plugins;

import static de.sklaholz.sahi.gradle.Constants.Constraints.MINIMAL_GRADLE_VERSION;
import static de.sklaholz.sahi.gradle.Constants.Extensions.SAHI_PRO_RUNNER_EXTENSION_NAME;
import static de.sklaholz.sahi.gradle.Constants.Plugin.NAME;
import static de.sklaholz.sahi.gradle.Constants.Tasks.SAHI_PRO_RUNNER_TASK_NAME;
import static java.lang.String.format;

import de.sklaholz.sahi.gradle.extensions.SahiProRunnerTaskExtension;
import de.sklaholz.sahi.gradle.tasks.SahiProRunnerTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginInstantiationException;
import org.gradle.util.GradleVersion;

public class SahiProRunnerPlugin implements Plugin<Project> {

  public void apply(final Project project) {
    checkGradleVersion();
    initializeConfigurations(project);
    registerTasks(project);
  }

  private void registerTasks(final Project project) {
    project.getTasks().register(SAHI_PRO_RUNNER_TASK_NAME, SahiProRunnerTask.class);
  }

  private void initializeConfigurations(final Project project) {
    project.getExtensions().create(SAHI_PRO_RUNNER_EXTENSION_NAME, SahiProRunnerTaskExtension.class, project);
  }

  void checkGradleVersion() {
    if (MINIMAL_GRADLE_VERSION.compareTo(GradleVersion.current()) > 0) {
      throw new PluginInstantiationException(format("%s requires %s and higher", NAME, MINIMAL_GRADLE_VERSION));
    }
  }
}
