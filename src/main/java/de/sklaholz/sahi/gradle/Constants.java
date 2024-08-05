package de.sklaholz.sahi.gradle;

import org.gradle.util.GradleVersion;

public final class Constants {

  public static class Plugin {

    public static final String ID = "de.sklaholz.sahi-pro-runner";
    public static final String NAME = "SahiPro Runner Plugin";
    public static final String GROUP_NAME = "sahi pro";
  }

  public static class Tasks {

    public static final String SAHI_PRO_RUNNER_TASK_NAME = "runSahiPro";
  }

  public static class Extensions {

    public static final String SAHI_PRO_RUNNER_EXTENSION_NAME = "sahiProRunner";
  }

  public static class Constraints {

    public static final GradleVersion MINIMAL_GRADLE_VERSION = GradleVersion.version("8.8");
  }
}
