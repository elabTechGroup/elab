package com.elab.core.configuration.entity;

import com.elab.core.utils.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author liuhx on 2017/1/2 13:53
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LogManager {
    /**
     * 日志记录所属系统名称ͳ
     */
    @XmlAttribute(name="system")
    public String systemName;

    /**
     * 是否开始日志debug模式
     */
    @XmlAttribute(name="isDebugEnabled")
    public Boolean isSystemDebugEnabled;

    /**
     * 日志包含的实现类型
     */
    @XmlElements(@XmlElement(name = "log", type = LogConfig.class))
    public List<LogConfig> emitters;

    public LogConfig getEmitterByName(String name) {
        LogConfig emitter = null;
        if(!StringUtils.isEmpty(name) && null != emitters && emitters.size() > 0) {
            for (int i = 0; i < emitters.size(); i++) {
                if(name.equals(emitters.get(i).getName())) {
                    emitter = emitters.get(i);
                    break;
                }
            }
        }
        return emitter;
    }

    /**
     * 默认调用的日志实现类
     */
    @XmlAttribute(name="defaultEmitter")
    public String defaultEmitterType;


    public String getDefaultEmitter() { return defaultEmitterType; }

    public void setDefaultEmitter(String defaultEmitter) {
        this.defaultEmitterType = defaultEmitter;
    }


    public List<LogConfig> getEmitters() { return emitters; }

    public void setEmitters(List<LogConfig> emitters) {
        this.emitters = emitters;
    }


    public Boolean getIsDebugEnabled() {
        return isSystemDebugEnabled;
    }

    public void setIsDebugEnabled(Boolean isDebugEnabled) {
        this.isSystemDebugEnabled = isDebugEnabled;
    }


    public String getSystem() {
        return systemName;
    }

    public void setSystem(String systemName) {
        this.systemName = systemName;
    }


}
