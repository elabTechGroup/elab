package com.elab.core.utils;

import java.util.*;

/**
 * 对Map集合的操作
 * @author liuhx on 2016/12/17 21:10
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MapUtils {

    /**
     * 根据传入的值，进行集合元素的删除
     * @param obj
     * @param iterator
     */
    private static void remove(Object obj,Iterator iterator){
        if(obj instanceof String){
            String str = (String)obj;
            if(StringUtils.isEmpty(str) || "%null%".equals(str)){
                iterator.remove();
            }
        }else if(obj instanceof Collection){
            Collection col = (Collection)obj;
            if(col==null||col.isEmpty()){
                iterator.remove();
            }

        }else if(obj instanceof Map){
            Map temp = (Map)obj;
            if(temp==null||temp.isEmpty()){
                iterator.remove();
            }

        }else if(obj instanceof Object[]){
            Object[] array =(Object[])obj;
            if(array==null||array.length<=0){
                iterator.remove();
            }
        }else{
            if(obj==null){
                iterator.remove();
            }
        }
    }

    /**
     * 移除map的空key
     * @param map
     * @return
     */
    public static void removeNullKey(Map map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            remove(obj, iterator);
        }
    }

    /**
     * 移除map中的value空值
     * @param map
     * @return
     */
    public static LinkedHashMap removeNullValue(LinkedHashMap map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            Object value = ObjectUtils.isNotEmpty(map.get(obj)) ? map.get(obj) : null;
            remove(value, iterator);
        }
        return map;
    }


    /**
     * 传入参数？替换成map中的key
     * @param sql
     * @param map
     * @return
     */
    public static String questionMarkReplace(String sql, Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (sql.trim().toLowerCase().startsWith("update")) {
                sql = sql.replaceFirst("\\s*"+obj+"\\s*=\\s*\\?\\s*", " "+obj+"=:"+obj+" ");
            } else {
                sql = sql.replaceFirst("\\?", ":" + obj);
            }
        }
        return sql;
    }


    /**
     * 字符串空替换成对象null
     * @param map
     * @return
     */
    public static LinkedHashMap emptyFormatNull(LinkedHashMap map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            Object value = ObjectUtils.isNotEmpty(map.get(obj)) ? map.get(obj) : null;
            if (null == value) {
                map.put(obj, null);
            }
        }
        return map;
    }


    public static void main(String[] args) {
        LinkedHashMap map = new LinkedHashMap();
//        map.put("b", "b");
        map.put("c", "c");
        map.put("a", "a");
        map.put("f", "f");
        map.put("e", "e");
        map.put("d", "d");
        String sql = questionMarkReplace("update table set a=?,c=?,b=?,d=? where f=? and e=?", map);


        String updateSql = "update enumeration set  name =?,asdf=?,remark=:remark,sdfsdf=?,a=:a, c=:c, ty=? where  id =?";

        while(true) {
            if (updateSql.indexOf(":") > 0) {
                updateSql = updateSql.replaceAll("(,?\\s*\\w*\\.?(?=[a-zA-Z|0-9])\\s*\\S+\\s*:[a-zA-Z|0-9]*+\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");
                continue;
            }else {
                break;
            }
        }
        /*if (updateSql.indexOf(":") > 0) {
//            updateSql = updateSql.replaceAll("(,?\\s*\\w*\\.?remark\\s*\\S+\\s*:[a-zA-Z|0-9]*+\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");
            updateSql = updateSql.replaceAll("(,?\\s*\\w*\\.?(?=[a-zA-Z|0-9])\\s*\\S+\\s*:[a-zA-Z|0-9]*+\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");

//            updateSql = updateSql.replaceAll("(,?\\s*\\w*\\.?=+\\s*\\S+\\s*:+\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");

        }*/
        System.out.println(updateSql);

    }

}
