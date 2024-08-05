package de.sklaholz.sahi;

import in.co.sahi.distributed.Autoheal;
import in.co.sahi.distributed.DDelete;
import in.co.sahi.distributed.DPull;
import in.co.sahi.distributed.DSahiRunner;
import in.co.sahi.distributed.DSync;
import in.co.sahi.distributed.SuiteInfo;
import net.sf.sahi.util.Utils;
import org.gradle.api.GradleException;

public class SahiProRunner {

  public void runSahi(final SahiProRunnerConfig config) {
    final String originFolderNew = Utils.makePathOSIndependent(Utils.getAbsolutePath(config.getOriginFolder()));
    final String suitePathNew = Utils.concatPaths(originFolderNew, config.getSuitePath()).replace("//", "/");
    final DSync dSync = new DSync();
    dSync.syncNodesSmartZip(
        config.getCsvSeparator(),
        config.getScriptExtensions(),
        config.getScenarioExtensions(),
        config.getMasterHost() + ":" + config.getMasterPort(),
        config.getDestFolder(), originFolderNew,
        config.getIgnorePattern(),
        suitePathNew,
        "java",
        "exposed_classes.txt",
        "true".equals(config.getIsAutohealEnabled()),
        "true".equals(config.getIsSyncConfig()),
        config.getConfigFolder());

    final SuiteInfo suiteInfo = createSuiteInfo(config);
    final DSahiRunner dSahiRunner = new DSahiRunner(suiteInfo);
    final String status = dSahiRunner.execute();

    final DDelete dDelete = new DDelete();
    dDelete.delete(config.getMasterHost(), config.getMasterPort(), config.getFilePath());
    if ("true".equalsIgnoreCase(config.getIsSyncConfig())) {
      try {
        final String configPath = DSync.getConfigDestFolder(config.getFilePath());
        dDelete.delete(config.getMasterHost(), config.getMasterPort(), configPath);
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
    if ("true".equals(config.getIsAutohealEnabled())) {
      DPull.pullAutohealFiles(
          config.getMasterHost(),
          config.getMasterPort(),
          Autoheal.getAutohealDirPath(config.getFilePath()),
          Autoheal.getAutohealDirPath(config.getScriptDir()),
          config.getIgnorePattern());
    }
//    try {
//      final String readTimeout = map.get("readTimeout");
//      if (readTimeout != null) {
//        Utils.setUrlConnectionReadTimeout(Integer.parseInt(readTimeout));
//        System.out.println("Updated ReadTimeout: " + Utils.getUrlConnectionReadTimeout());
//      }
//    } catch(final Exception e) {
//      System.out.println("Could not set read timeout due to, " + e.getMessage());
//    }
    new DPull().pullFile(
        config.getMasterHost(),
        config.getMasterPort(),
        config.getOriginFolderLogs(),
        config.getDestFolderLogs(),
        config.getIgnorePattern());

    if ("FAILURE".equals(status) || "ABORTED".equals(status)) {
      throw new GradleException("Sahi scripts failed...!!!");
    }
  }

  private SuiteInfo createSuiteInfo(final SahiProRunnerConfig config) {
    final SuiteInfo suiteInfo = new SuiteInfo(config.getSuitePath(), config.getBaseURL(), config.getBrowser());
    suiteInfo.setHost(config.getMasterHost());
    suiteInfo.setPort(config.getMasterPort());
    suiteInfo.setThreads(config.getThreads());
    suiteInfo.setNodes(config.getNodes());
    suiteInfo.setIgnorePattern(config.getIgnorePattern());
    suiteInfo.setLogsInfo(config.getLogsInfo());
    suiteInfo.setScriptsPathInitiator(config.getScriptsPathInitiator());
    suiteInfo.setScriptsPathMaster(config.getScriptsPathMaster());
    suiteInfo.setIsDifferentMasterS(config.getIsDifferentMasterS());
    suiteInfo.setIsSyncConfig(config.getIsSyncConfig());
    return suiteInfo;
  }
}
