package com.elab.log.asepct;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC层面的拦截
 *
 * @author liuhx on 2017/1/11 19:41
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class TimeInterceptor implements HandlerInterceptor {

    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
    private final Logger logger = Logger.getLogger(CatAspect.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            Long startTime = System.currentTimeMillis();
            logger.debug(" 请求开始 " + request.getRequestURL().toString() + " 目标方法 : " + h.getMethod().getName());
            request.setAttribute("startTime", startTime);
            request.setAttribute("method", h.getMethod().getName());
            request.setAttribute("uri", request.getRequestURL().toString());
            Transaction transaction = Cat.getProducer().newTransaction("interfaces",
                    h.getMethod().getDeclaringClass().getName().concat("." + h.getMethod().getName()));
            Cat.getProducer().logEvent("IP", request.getRemoteHost(), Message.SUCCESS, "");
            Cat.getProducer().logEvent("Route", request.getRequestURL().toString(), Message.SUCCESS, "");
            request.setAttribute("transaction", transaction);
            tranLocal.set(transaction);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod) {
            Long startTime = (Long) request.getAttribute("startTime");
            String method = request.getAttribute("method").toString();
            String uri = request.getAttribute("uri").toString();
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
//            HandlerMethod h = (HandlerMethod) handler;
//            method = "类名：" + h.getBean().getClass().getName() + " | 方法名：" + method;
            logger.debug("请求结束 - 方法 : " + method + " URI:" + uri + " 耗时:" + executeTime + "ms");

//            NameValueCollection props = new NameValueCollection();
//            props.put("方法：", method);
//            props.put("URI：", uri);
//            props.put("方法耗时：", executeTime + "ms");
//            entry.setProperties(props);
//            LogUtils.send(entry);
            /*
            Transaction transaction = (Transaction)request.getAttribute("transaction");
            transaction.setStatus(Transaction.SUCCESS);
            transaction.complete();*/
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        Transaction transaction = tranLocal.get();
        if (null != transaction) {
//            tranLocal.remove();
            if (!transaction.isCompleted()) {
                transaction.setStatus(Transaction.SUCCESS);
                transaction.complete();
            }
        }
    }
}
