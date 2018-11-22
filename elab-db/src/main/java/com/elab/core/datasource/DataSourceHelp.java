package com.elab.core.datasource;

/**
 * @author liuhx on 2016/12/8 09:42
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public abstract class DataSourceHelp {

    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }

    //获取数据源
    public static String getDataSource() {
        return dataSources.get();
    }
    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }

}
