package com.elab.core.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:数据库对应字段实体注解类
 * @Author: Liukx on 2017/4/5 - 17:24
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 数据库对应字段
     *
     * @return
     */
    String name() default "";
}
