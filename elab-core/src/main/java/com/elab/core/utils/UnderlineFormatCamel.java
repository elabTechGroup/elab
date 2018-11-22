package com.elab.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下划线转驼峰法
 * @author liuhx on 2017/4/5 10:47
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class UnderlineFormatCamel {

    private static Pattern pattern1=Pattern.compile("([A-Za-z\\d]+)(_)?");
    private static Pattern pattern2=Pattern.compile("[A-Z]([a-z\\d]+)?");

    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underlineToCamel(String line,boolean smallCamel) {
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();

        Matcher matcher=pattern1.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰法,默认小驼峰（首字母小写）
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String underlineToCamel(String line) {
        return underlineToCamel(line, true);
    }

    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();

        Matcher matcher=pattern2.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line="I_HAVE_AN_IPANG3_PIG";
        String camel=underlineToCamel(line,true);
        System.out.println(camel);
        String houseid = underlineToCamel("layout_id", false);
        System.out.println(houseid);
//        System.out.println(camel2Underline(camel));

    }
}
