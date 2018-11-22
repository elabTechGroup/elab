package com.elab.core.aop;

import com.elab.core.datasource.DataSource;
import com.elab.core.datasource.DataSourceHelp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 数据源的切换
 *
 * @author liuhx on 2016/12/8 13:35
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class DataSourceAspect {

    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DataSourceHelp.setDataSource(data.name());
                System.out.println("数据源： " + data.name());
            }
        } catch (Exception e) {
        }
    }

    public void after(JoinPoint point, Object retValue) {

    }
}
