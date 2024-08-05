package de.sklaholz.sahi.gradle.tasks;

import de.sklaholz.sahi.SahiProRunner;
import de.sklaholz.sahi.SahiProRunnerConfig;
import de.sklaholz.sahi.gradle.extensions.SahiProRunnerTaskExtension;
import lombok.Getter;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

public abstract class SahiProRunnerTask extends DefaultTask {

  @Getter
  @Internal
  private final SahiProRunnerTaskExtension config = getProject().getExtensions().getByType(SahiProRunnerTaskExtension.class);

  private final SahiProRunner sahiProRunner = new SahiProRunner();

  @TaskAction
  public void executeSahi() {
    final SahiProRunnerConfig runnerConfig = new SahiProRunnerConfig(
        config.getScriptDir(),
        config.getMasterHost(),
        config.getMasterPort(),
        config.getScriptName(),
        config.getBrowser(),
        config.getBaseURL(),
        config.getThreads(),
        config.getNodes(),
        getProject().getLayout().getBuildDirectory().dir("reports/sahi/html/").get().getAsFile().getAbsolutePath());
    sahiProRunner.runSahi(runnerConfig);
  }
}
