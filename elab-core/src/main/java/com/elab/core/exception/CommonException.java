package com.elab.core.exception;

import com.elab.core.bean.Info;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常定义
 *
 * @author Liukx
 * @create 2017-04-10 15:28
 * @email liukx@elab-plus.com
 **/
@ControllerAdvice
public class CommonException {

    private String defaultSystemError = "CORE.ERROR.001";
    private String defaultParamsError = "CORE.PARAMS.001";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exception(Exception ex) {
        Info info = new Info();
        info.setSuccess(false);
        info.setErrorCode(defaultSystemError);
        info.setMessage(ex.getMessage().toString());
        return info;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object methodArgumentNotValidException(Exception ex) {
        StringBuffer sb = new StringBuffer();
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
            for (int i = 0; i < allErrors.size(); i++) {
                FieldError objectError = (FieldError) allErrors.get(i);
                sb.append(objectError.getField() + " 字段值 " + objectError.getRejectedValue() + objectError.getDefaultMessage());
            }
        }
        Info info = new Info();
        info.setSuccess(false);
        info.setErrorCode(defaultParamsError);
        info.setMessage(sb.toString());
        return info;
    }

    @ExceptionHandler(CoreException.class)
    @ResponseBody
    public Object coreException(CoreException ex) {
        Info info = new Info();
        info.setSuccess(false);
        info.setErrorCode(ex.getErrorCode());
        info.setMessage(ex.getMessage());
        return info;
    }
}
