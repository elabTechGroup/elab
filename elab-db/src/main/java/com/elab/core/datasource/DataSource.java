package com.elab.core.datasource;

import java.lang.annotation.*;

/**
 * @author liuhx on 2016/12/8 10:37
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSource.mvp;

    public static String mvp = "default";

    public static String system = "system";

    public static String test = "test";

    public static String onlyRead = "onlyRead";
}
