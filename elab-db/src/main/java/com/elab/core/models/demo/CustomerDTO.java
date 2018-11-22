package com.elab.core.models.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuhx on 2017/4/5 13:19
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class CustomerDTO {

    private String id;
    private String employeeNo;
    private String employeeName;
    private String email;
    private String phone;
    private String status;
    private String password;
    private String addDate;
    private String modifydate;
    private String addoperator;
    private String remark;

    public CustomerDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getModifydate() {
        return modifydate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public String getAddoperator() {
        return addoperator;
    }

    public void setAddoperator(String addoperator) {
        this.addoperator = addoperator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
