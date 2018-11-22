package com.elab.core.configuration.entity;

import com.elab.core.collections.MapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

/**
 * @author liuhx on 2017/1/2 13:53
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LogConfig {
    /**
     * 日志名称
     */
    @XmlAttribute(name="name")
    public String logName;

    /**
     * 日志类型
     */
    @XmlAttribute(name="type")
    public String objType;


    /**
     * 是否记录debug日志
     */
    @XmlAttribute(name="logDebug")
    public Boolean logDebug;


    /**
     * 是否记录Info日志
     */
    @XmlAttribute(name="logInfo")
    public Boolean logInfo;


    /**
     * 日志配置参数
     */
    @XmlJavaTypeAdapter(MapAdapter.class)
    public HashMap<String,String> properties;

    public HashMap<String,String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String,String> properties) {
        this.properties = properties;
    }

    public String getName() {
        return logName;
    }

    public void setName(String name) {
        this.logName = name;
    }

    public String getType() {
        return objType;
    }

    public void setType(String type) {
        this.objType = type;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Boolean getLogDebug() {
        return logDebug;
    }

    public void setLogDebug(Boolean logDebug) {
        this.logDebug = logDebug;
    }

    public Boolean getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(Boolean logInfo) {
        this.logInfo = logInfo;
    }
}
