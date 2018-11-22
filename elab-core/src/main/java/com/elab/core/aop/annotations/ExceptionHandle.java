package com.elab.core.aop.annotations;

import java.lang.annotation.*;

/**
 * 处理异常的AOP注解
 *
 * @author liuhx on 2017/1/5 20:07
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandle {

    /**
     * 负责人名称
     *
     * @return
     */
    String username() default "";

    /**
     * 定义异常来源于哪个类
     *
     * @return
     */
    Class ModuleName();

    /**
     * 异常是否需要往外部调用方抛
     *
     * @return
     */
    boolean Throwable() default false;
}
