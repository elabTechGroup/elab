package com.elab.core.configuration.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 第二层节点的子节点
 * <enumerations>
 *     <enumeration type="" remark="">
 *         <value name="" val=""></value>
 *     </enumeration>
 * </enumerations>
 * @author liuhx on 2017/3/22 14:11
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ValueMode {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "val")
    private String val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
