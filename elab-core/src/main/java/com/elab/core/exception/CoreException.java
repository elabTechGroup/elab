package com.elab.core.exception;

/**
 * @author liuhx on 2016/12/8 15:57
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class CoreException extends RuntimeException {

    private String errorCode;

    public CoreException() {
        super();
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreException(Throwable cause) {
        super(cause);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
