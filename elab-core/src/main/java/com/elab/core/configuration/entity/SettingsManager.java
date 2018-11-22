package com.elab.core.configuration.entity;

import com.elab.core.utils.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

/**
 * @author liuhx on 2017/1/2 13:46
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingsManager {
    /**
     * 参数列表
     */
    @XmlElements(@XmlElement(name = "property", type = SettingConfig.class))
    public List<SettingConfig> properties;

    public SettingConfig getPropertyByName(String name) {
        SettingConfig property = null;
        if(!StringUtils.isEmpty(name) && null != properties && properties.size() > 0) {
            for (int i = 0; i < properties.size(); i++) {
                if(name.equals(properties.get(i).getName())) {
                    property = properties.get(i);
                    break;
                }
            }
        }
        return property;
    }
}

