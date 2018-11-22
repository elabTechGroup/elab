package com.elab.core.configuration.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * 第二层节点
 * <enumerations>
 *     <enumeration type="" remark=""></enumeration>
 * </enumerations>
 * @author liuhx on 2017/3/22 13:48
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumerationMode {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "remark")
    private String remark;

    @XmlElement(name = "value")
    private List<ValueMode> values;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ValueMode> getValues() {
        return values;
    }

    public void setValues(List<ValueMode> values) {
        this.values = values;
    }
}
