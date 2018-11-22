package com.elab.core.controller;

import com.elab.core.CoreConstant;
import com.elab.core.utils.DESUtils;
import com.elab.core.utils.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author liuhx on 2017/1/9 17:58
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;


    public HttpServletRequest getRequest() {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpSession getSession() {
        session = request.getSession();
        return session;
    }

    /**
     * 获取request请求头里面的token
     * @return
     */
    private String getAuthToken() {
        return getRequest().getHeader(CoreConstant.ACCESS_TOKEN);
    }


    /**
     * 获取request请求头里面的head参数
     * @return
     */
    private String getHead() {
        return getRequest().getHeader(CoreConstant.HEAD);
    }
    /**
     * 获取当前用户ID
     * @return
     */
    public int getCurrentCustomerId(){
        try {
            String decodeValue = DESUtils.decode(CoreConstant.AUTH_TOKEN_KEY, getAuthToken());
            System.out.println("用户id>>>>>"+decodeValue);
            if(!StringUtils.isEmpty(decodeValue) && decodeValue.contains("|")) {
                String[] temp = decodeValue.split("\\|");
                if(temp.length == 2 && !StringUtils.isEmpty(temp[1]) && StringUtils.isNumeric(temp[1])) {
                    return StringUtils.toNumber(temp[1]);
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public String getStringCustomerId(){
        try {
            String decodeValue = DESUtils.decode(CoreConstant.AUTH_TOKEN_KEY, getAuthToken());
            if(!StringUtils.isEmpty(decodeValue) && decodeValue.contains("|")) {
                String[] temp = decodeValue.split("\\|");
                if(temp.length == 2 && !StringUtils.isEmpty(temp[1]) && StringUtils.isNumeric(temp[1])) {
                    return temp[1];
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

}
