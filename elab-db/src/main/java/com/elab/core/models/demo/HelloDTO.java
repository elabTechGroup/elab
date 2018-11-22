package com.elab.core.models.demo;

import com.elab.core.aop.annotations.Column;

/**
 * @author liuhx on 2017/4/5 11:28
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class HelloDTO  {
    private String id;
    private String name;
    private String value;
    private String type;
    @Column(name="parentcode")
    private String code;
    private String remark;
    private String status;

    public HelloDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
