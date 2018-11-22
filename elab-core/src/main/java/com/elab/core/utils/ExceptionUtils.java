package com.elab.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 把异常信息从堆栈中获取到字符串中
 * @author liuhx on 2017/1/2 14:39
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class ExceptionUtils {
    /**
     * 把异常信息从堆栈中获取到字符串中
     * @param e
     * @return
     */
    public static String getExceptionInfo(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();

        try {
            sw.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args) {
        Exception e = new Exception();
        String info = getExceptionInfo(e);
        System.out.println(info);
    }
}
