package com.elab.log.asepct;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.elab.core.aop.annotations.ExceptionHandle;
import com.elab.core.bean.Info;
import com.elab.core.utils.ObjectUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Description: CAT拦截器切入
 * @Author: Liukx on 2018/2/22 - 14:21
 */
public class CatAspect {

    private final Logger logger = Logger.getLogger(CatAspect.class);

    /**
     * 环绕通知方法
     *
     * @param pjp
     * @throws Exception
     */
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Exception {
        Object proceed = null;
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();

        // 获取参数的名称
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        Method objMethod = classTarget.getMethod(methodName, par);

        // 注解是否存在
        boolean annotationExist = objMethod.isAnnotationPresent(ExceptionHandle.class);

        // 构建一个类和方法的路径
        String value = pjp.getTarget().getClass().getSimpleName() + "." + methodName;
        String fullMethodName = value;
        if (annotationExist) {
            String username = objMethod.getAnnotation(ExceptionHandle.class).username();
            if (ObjectUtils.isNotEmpty(username)) {
                value = username;
                logger.debug("--------------------进入负责人[" + value + "]区域------------------------");
            }
        }
        Transaction t = Cat.newTransaction(value, fullMethodName);
        logger.info(" 开始执行方法 " + method.getName() + " 参数:" + JSON.toJSONString(pjp.getArgs()));
        try {
            proceed = pjp.proceed();
            t.setStatus(Transaction.SUCCESS);
        } catch (Throwable e) {
            t.setStatus(e);
            logger.error("[" + value + "]", e);
            throw new RuntimeException(e);
        } finally {
            t.complete();
            if (proceed instanceof Info) {
                Info info = (Info) proceed;
                logger.info("出参结果 : " + info.isSuccess() + " 消息内容 : " + info.getMessage());
            }
            logger.info(" 结束执行方法 " + method.getName());
        }
        return proceed;
    }

}
