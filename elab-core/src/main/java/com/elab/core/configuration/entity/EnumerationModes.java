package com.elab.core.configuration.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 最外层xml标签，根节点
 * <enumerations></enumerations>
 * @author liuhx on 2017/3/22 13:56
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "enumerations")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumerationModes {

   @XmlElement(name = "enumeration")
   private List<EnumerationMode> enumeration;

    public List<EnumerationMode> getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(List<EnumerationMode> enumeration) {
        this.enumeration = enumeration;
    }
}
