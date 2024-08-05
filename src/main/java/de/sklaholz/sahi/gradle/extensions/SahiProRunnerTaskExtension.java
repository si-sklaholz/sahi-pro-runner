package de.sklaholz.sahi.gradle.extensions;

import javax.inject.Inject;
import lombok.Data;
import org.gradle.api.Project;

@Data
public class SahiProRunnerTaskExtension {

  /**
   * property scriptDir Specifies the path to the scripts folder. The default value is
   * "${project.projectDir.path}/sahi"
   */
  private String scriptDir;

  /**
   * property masterHost Hostname of the different Master server where Sahi is running (Can be IP as well)
   */
  private String masterHost;

  /**
   * property masterPort Port on which Sahi is running
   */
  private String masterPort;

  /**
   * property scriptName Relative path to the script/suite/data driven suite. It is relative to scriptDir.
   */
  private String scriptName;

  /**
   * property browser The browser on which the suite file plays back
   */
  private String browser;

  /**
   * property baseURL Specifies URL which is the starting URL for all the scripts in the suite
   */
  private String baseURL;

  /**
   * property threads Number of simultaneous browser instances on which sahi tests will be run. The default value is
   * "5"
   */
  private String threads = "5";

  /**
   * property nodes Each node attribute specifies a machine on which the tests should run. Add as many node entries as
   * there are machines to run. The nodes may or may not include the Master machine. If the Master machine is not
   * included, scripts will not be run on the Master. There can be 1 or more nodes.
   */
  private String nodes;

  @Inject
  public SahiProRunnerTaskExtension(final Project project) {
    this.scriptDir = project.getProjectDir().getPath() + "/sahi";
  }
}
