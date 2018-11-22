package com.elab.core.configuration.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 解析XML中的<settings>字段，它对应的属性是name和value
 * @author liuhx on 2017/1/2 13:46
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingConfig {
    /**
     * 属性名称
     */
    @XmlAttribute(name="name")
    private String name;
    /**
     * 属性值
     */
    @XmlAttribute(name="value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
