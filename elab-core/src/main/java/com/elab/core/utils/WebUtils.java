package com.elab.core.utils;

import com.elab.core.CoreConstant;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author liuhx on 2017/3/15 12:03
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class WebUtils {
    /* ----- ----- ----- ----- 请求响应对象获取 ----- ----- ----- ----- */
    /* NOTE 这些方法都依赖于Spring Web以及RequestContextListener的部署 */
    /**
     * 返回当前Servlet上下文的请求参数对象
     *
     * @return 返回当前Servlet上下文的请求参数对象
     */
    public static ServletRequestAttributes getRequestAttributes() {
        // NOTE 这里不使用getRequestAttributes()是为了防止RequestContextListener没有部署, 返回不可控的null值
        // NOTE currentRequestAttributes()在RequestContextListener未部署，取不到对象时，会直接抛出异常
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        // 如果是ServletRequestAttributes,返回对象
        if (requestAttributes instanceof ServletRequestAttributes) {
            return (ServletRequestAttributes) requestAttributes;
        } else {
            // 一般出现这个异常原因是没有部署RequestContextListener且启用了JSF环境
            throw new IllegalStateException("当前上下文环境非Servlet环境");
        }
    }

    /**
     * 返回当前上下文的请求对象
     *
     * @return 返回当前上下文的请求对象
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 返回当前上下文的响应对象
     *
     * @return 返回当前上下文的响应对象
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 返回当前上下文Session Id
     *
     * @return 返回当前上下文Session Id
     */
    public static String getSessionId() {
        return getRequestAttributes().getSessionId();
    }

    /**
     * 返回当前上下文Session
     *
     * @param isCreate 如果session不存在, 是否创建
     * @return 返回当前上下文Session
     */
    public static HttpSession getSession(boolean isCreate) {
        return getRequest().getSession(isCreate);
    }

    /**
     * 获取Spring Web应用程序上下文<br>
     * 只能得到顶层WebApplicationContext, 而无法得到MVC DispatchServlet管理的ApplicationContext<br>
     * 建议优先考虑Spring提供的ApplicationContextAware
     *
     * @return 返回当前Spring Web应用程序上下文
     */
    public static WebApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    /**
     * 获取Servlet上下文<br>
     * 建议优先考虑Spring提供的ServletContextAware
     *
     * @return 返回当前Servlet上下文
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }


    /**
     * 获取request请求头里面的token
     * @return
     */
    private static String getAuthToken() {
        return getRequest().getHeader(CoreConstant.ACCESS_TOKEN);
    }

    /**
     * 获取客户ID
     * @return
     */
    public static String getStringCustomerId() {
        try {
            String decodeValue = DESUtils.decode(CoreConstant.AUTH_TOKEN_KEY, getAuthToken());
            if(!StringUtils.isEmpty(decodeValue) && decodeValue.contains("|")) {
                String[] temp = decodeValue.split("\\|");
                if(temp.length == 2 && !StringUtils.isEmpty(temp[1])) {
                    return temp[1];
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
