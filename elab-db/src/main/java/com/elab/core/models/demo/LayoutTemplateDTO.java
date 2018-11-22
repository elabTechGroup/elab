package com.elab.core.models.demo;

import com.elab.core.aop.annotations.Column;

/**
 * @author liuhx on 2017/4/5 13:36
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class LayoutTemplateDTO {
    @Column(name = "id")
    private String sid;
    private String name;
    private String buildingNum;
    private String frameCode;
    private String templateCode;
    private String layoutplanCode;
    private String remark;
    private String status;
    private String isUpdate;

    public LayoutTemplateDTO() {

    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getFrameCode() {
        return frameCode;
    }

    public void setFrameCode(String frameCode) {
        this.frameCode = frameCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getLayoutplanCode() {
        return layoutplanCode;
    }

    public void setLayoutplanCode(String layoutplanCode) {
        this.layoutplanCode = layoutplanCode;
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

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }
}
