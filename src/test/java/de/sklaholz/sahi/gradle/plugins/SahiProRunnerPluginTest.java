package de.sklaholz.sahi.gradle.plugins;

import static de.sklaholz.sahi.gradle.Constants.Plugin.ID;
import static org.assertj.core.api.BDDAssertions.then;

import de.sklaholz.sahi.gradle.Constants.Tasks;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

class SahiProRunnerPluginTest {

  @Test
  void shouldRegisterPluginSuccessfully() {
    // given
    final Project project = ProjectBuilder.builder().build();

    // when
    project.getPlugins().apply(ID);

    // then
    then(project.getPluginManager().hasPlugin(ID)).isTrue();
  }

  @Test
  void shouldRegisterTaskSuccessfully() {
    // given
    final Project project = ProjectBuilder.builder().build();

    // when
    project.getPlugins().apply(ID);

    // then
    then(project.getTasks().findByName(Tasks.SAHI_PRO_RUNNER_TASK_NAME)).isNotNull();
  }
}
