package de.sklaholz.sahi;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;

@Getter
public class SahiProRunnerConfig {

  private final String scriptDir;
  private final String masterHost;
  private final String masterPort;
  private final String scriptName;
  private final String browser;
  private final String baseURL;
  private final String threads;
  private final String nodes;
  private final String destFolderLogs;
  private final String suitePath;
  private final String ignorePattern;
  private final String logsInfo;
  private final String scriptsPathInitiator;
  private final String scriptsPathMaster;
  private final String isDifferentMasterS;
  private final String csvSeparator;
  private final String originFolder;
  private final String destFolder;
  private final String scriptExtensions;
  private final String scenarioExtensions;
  private final String isAutohealEnabled;
  private final String isSyncConfig;
  private final String configFolder;
  private final String filePath;
  private final String originFolderLogs;

  public SahiProRunnerConfig(
      final String scriptDir,
      final String masterHost,
      final String masterPort,
      final String scriptName,
      final String browser,
      final String baseURL,
      final String threads,
      final String nodes,
      final String destFolderLogs) {

    this.scriptDir = scriptDir;
    this.masterHost = masterHost;
    this.masterPort = masterPort;
    this.scriptName = scriptName;
    this.browser = browser;
    this.baseURL = baseURL;
    this.threads = threads;
    this.nodes = nodes;
    this.destFolderLogs = destFolderLogs;

    final String uniqueId = new SimpleDateFormat("dd-MM-yyyy___HH_mm_ss.SS").format(new Date());
    final String masterHtmlLogsDir = "logs/temp/html/" + uniqueId;
    final String masterStagingPathUnique = "temp/scripts/staging" + uniqueId;
    this.logsInfo = "html:" + masterHtmlLogsDir;
    this.scriptsPathMaster = masterStagingPathUnique;
    this.destFolder = masterStagingPathUnique;
    this.filePath = masterStagingPathUnique;
    this.originFolderLogs = masterHtmlLogsDir;
    this.suitePath = this.scriptName;
    this.scriptsPathInitiator = scriptDir;
    this.originFolder = scriptDir;
    this.ignorePattern = ".*(svn|copied).*";
    this.isDifferentMasterS = "true";
    this.csvSeparator = ",";
    this.scriptExtensions = "sah;sahi;js;";
    this.scenarioExtensions = ".s.csv;xls;xlsx";
    this.isAutohealEnabled = "false";
    this.isSyncConfig = "false";
    this.configFolder = "";
  }
}
