package com.elab.core.configuration.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author liuhx on 2017/1/2 13:43
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlRootElement(name = "global")
public class GlobalConfig {
    /**
     * 日志相关配置
     */
    @XmlElement(name = "logManager")
    public LogManager lm;

    /**
     * 参数相关配置
     */
    @XmlElement(name = "settings")
    public SettingsManager st;

    /**
     * 系统版本号
     */
    @XmlAttribute(name = "version")
    public String versionNo;

    public LogManager getLogManager() { return lm; }

    public void setLogManager(LogManager logManager) {
        this.lm = logManager;
    }

    public String getVersion() {
        return versionNo;
    }

    public void setVersion(String version) {
        this.versionNo = version;
    }

    public SettingsManager getSettings() {
        return st;
    }

    public void setSettings(SettingsManager st) {
        this.st = st;
    }
}