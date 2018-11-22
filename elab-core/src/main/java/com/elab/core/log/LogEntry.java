package com.elab.core.log;

import com.elab.core.collections.NameValueCollection;
import com.elab.core.configuration.ConfigManager;
import com.elab.core.utils.DateUtils;
import com.elab.core.utils.StringUtils;

import java.util.UUID;

/**
 * @author liuhx on 2017/1/2 14:20
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class LogEntry {

    private String logID;

    private String systemName;

    public String moduleName;

    public String content;

    public String logServerIP;

    public LogLevel logLevel;

    public String logDate;

    public String processTime;

    public String[] contents;

    public String format;

    public LogEntry(String moduleName){
        this(moduleName, "", LogLevel.Info);
    }

    public LogEntry(String moduleName, String content){
        this(moduleName, content, LogLevel.Info);
    }

    public LogEntry(String moduleName, String content, LogLevel logLevel){
        this.logID = UUID.randomUUID().toString();
        this.systemName = ConfigManager.getLogManager().systemName;
        this.moduleName = moduleName;
        this.content = content;
        this.logLevel = logLevel;
        this.logDate = DateUtils.getCurrentTime();
    }

    public LogEntry(String moduleName, String format, String... contents){
        this.logID = UUID.randomUUID().toString();
        this.systemName = ConfigManager.getLogManager().systemName;
        this.moduleName = moduleName;
        this.format = format;
        this.contents = contents;
        this.logLevel = LogLevel.Info;
        this.logDate = DateUtils.getCurrentTime();
    }

    public String getLogID() {
        return logID;
    }

    public NameValueCollection getProperties() {
        return properties;
    }

    public void setProperties(NameValueCollection properties) {
        this.properties = properties;
    }

    public NameValueCollection properties;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getLogServerIP() {
        if(null == logServerIP || logServerIP.length() < 0) {
            logServerIP = StringUtils.getLocalNetWorkIp();
        }
        return logServerIP;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
