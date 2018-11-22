package com.elab.core.bean;

import com.elab.core.utils.DateUtils;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author liuhx on 2016/12/11 15:12
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class BaseInfo implements Serializable {
    private Boolean success = Boolean.valueOf(true);
    private String errorCode;
    private String message;
    private String timestamp = DateUtils.getCurrentTime();                       //服务器的时间戳
    private Map<String, Object> extension;          // 扩展字段

    public BaseInfo() {
    }

    public BaseInfo(Boolean success, String errorCode, String message) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success.booleanValue();
    }

    public void setSuccess(boolean success) {
        this.success = Boolean.valueOf(success);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }
}
