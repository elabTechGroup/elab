package com.elab.core.sql;


import com.elab.core.sql.config.SqlKit;
import com.elab.core.utils.MapUtils;
import com.elab.core.utils.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhx on 2016/12/14 10:39
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class SqlOperator {



    public static String processNull(String sqlString, LinkedHashMap<String, Object> params) {
        String sql = sqlString;
        int num = 0;
        for (Map.Entry entry : params.entrySet()) {
            Object value = entry.getValue();
            if (ObjectUtils.isEmpty(value)) {
                value = null;
            }
            String key = entry.getKey().toString();
            if (null == value || value.equals("%null%")) {
                if (key.matches("[0-9]{1}[a-z]+")) {
                    sql = sql.replaceFirst("(\\w*\\.?" + key.substring(1) + "\\b\\s*\\S+\\s*:" + key + "\\s*\\)?)(\\s*and\\s*|\\s*or\\b\\s*|\\s*)", "  ");
                } else {
                    sql = sql.replaceFirst("(\\w*\\.?" + key + "\\b\\s*\\S+\\s*:" + key + "\\s*\\)?)(\\s*and\\s*|\\s*or\\b\\s*|\\s*)", "  ");
                    String strKey = key.substring(0, key.length()-1);
                    sql = sql.replaceFirst("(\\w*\\.?" + strKey + "\\b\\s*\\S+\\s*:" + key + "\\s*\\)?)(\\s*and\\s*|\\s*or\\b\\s*|\\s*)", "  ");
                    sql = sql.replaceFirst("(\\w*\\.?" + strKey + "\\b\\s*\\S+\\s*:" + key + "\\s*\\)?)(\\s*and\\s*|\\s*or\\b\\s*|\\s*)", "  ");
                    sql = sql.replaceFirst("(\\w*\\.?" + key + "\\b\\s*\\S+\\s*\\(:" + key + "\\)\\s*\\)?)(\\s*and\\s*|\\s*or\\b\\s*|\\s*)", "  ");
                }
                sql = sql.replaceAll("(and\\s*\\(\\s*order\\s*by\\s*|or\\s*order\\s*by\\s*)", " order by ");
                sql = sql.replaceAll("(and\\s*\\(\\s*group\\s*by\\s*|or\\s*group\\s*by\\s*)", " group by ");
                sql = sql.replaceAll("(and\\s*\\(\\s*$|or\\s*$)", "  ");
                sql = sql.replaceAll("(and\\s*order\\s*by\\s*|or\\s*order\\s*by\\s*)", " order by ");
                sql = sql.replaceAll("(and\\s*group\\s*by\\s*|or\\s*group\\s*by\\s*)", " group by ");
                sql = sql.replaceAll("(and\\s*$|or\\s*$)", "  ");
                num++;
            }

            if (num == params.size()) {
//                sql = sql.replaceFirst(" where ", " ");
                sql = sql.replaceAll("where\\s*group", " group ");
                sql = sql.replaceAll("where\\s*order", " order ");
                sql = sql.replaceAll("where\\s*$", "  ");
            }

        }
        sql = sql.replaceAll("\\s(and|or)\\s$", " ");
        sql = sql.replaceAll("(and|or)\\s*where", " where ");

        return sql;
    }


    public static String processNullForUpdate(String sqlString, LinkedHashMap<String, Object> params) {
        params = MapUtils.emptyFormatNull(params);
        String sql = sqlString;
        String startSQL = sql.substring(0, sql.indexOf("set")).concat("set ");
        String setSQL = sql.substring(sql.lastIndexOf("set")+3, sql.indexOf("where"));
        String whereSQL = sql.substring(sql.lastIndexOf("where")+5, sql.length());
//        System.out.println("原始update-SQL:"+sql);

        String[] sqlColumns = setSQL.split(",");
        for (String column : sqlColumns) {
            String field = column.substring(column.lastIndexOf(":")+1, column.length());
            if (!params.containsKey(field.trim())) {
                setSQL = setSQL.replaceFirst(":" + field.trim() + "\\b", " haixiaonull ");
            }
        }
        String replaceSetSQL = setSQL.replaceAll("(,\\s*?\\w*\\s*=\\s*haixiaonull\\b\\s*)", "  ");
        replaceSetSQL = replaceSetSQL.replaceAll("(,?\\w*\\s*=\\s*haixiaonull\\b\\s*)", "  ");
        /*for (Map.Entry entry : params.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey().toString();

            if (value == null) {
                setSQL = setSQL.replaceAll("(\\s*\\w*\\.?" + key + "\\s*\\S+\\s*:" + key + "\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)",
                        " "+key+"=null ");
                setSQL = setSQL.replaceAll("(and\\s*$|or\\s*$)", "  ");
                whereSQL = whereSQL.replaceAll("(,?\\s*\\w*\\.?" + key + "\\s*\\S+\\s*:" + key + "\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");
                whereSQL = whereSQL.replaceAll("(and\\s*$|or\\s*$)", "  ");
            } else if (value != null && value.getClass().isArray()) {
                Object[] arr = (Object[]) value;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == null) {
                        setSQL = setSQL.replaceAll("(,?\\s*\\w*\\.?" + key + "\\s*\\S+\\s*:" + key + "\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)",
                                " "+key+"=null, ");
                        whereSQL = whereSQL.replaceAll("(,?\\s*\\w*\\.?" + key + "\\s*\\S+\\s*:" + key + "\\b\\s*\\)?)(\\s*and\\s*|\\s*or\\s*|\\s*)", "  ");
                        whereSQL = whereSQL.replaceAll("(and\\s*$|or\\s*$)", "  ");
                    }
                }
            }
        }
        String resultSQL = startSQL.concat(setSQL).concat(" where ").concat(whereSQL);
        */
        String resultSQL = startSQL.concat(replaceSetSQL).concat(" where ").concat(whereSQL);
        resultSQL = resultSQL.replaceAll("(and\\s*$|or\\s*$)", " ");
        resultSQL = resultSQL.replaceAll(",\\s*where\\s*", " where ");
        resultSQL = resultSQL.replaceAll("set\\s*,\\s*", "set ");
//        System.out.println("过滤update-SQL："+resultSQL);
        return resultSQL;

    }


    private static String processNullForDelete(String sqlString, LinkedHashMap<String, Object> params) {
        // delete 不支持参数替换，传N个参数就代表SQL文件中肯定会有N个需要替换
        return sqlString;

    }

    private static String processNullForInsert(String sqlString, LinkedHashMap<String, Object> params) {
        params = MapUtils.emptyFormatNull(params);
        String sql = sqlString;

        String columnString = sql.substring(sql.indexOf("(") + 1, sql.indexOf(")"));
        String[] sqlColumns = columnString.split(",");

        for (String column : sqlColumns) {
            if (!params.containsKey(column.trim())) {
//                sql = sql.replaceFirst(":" + column.trim() + "\\b", " null ");
                sql = sql.replaceFirst(":" + column.trim() + "\\b", " null ");
            }
        }


        return sql;
    }





    private static String parseSQLForIn(String sql, LinkedHashMap<String, Object> params) {


        StringBuilder sqlBuffer = new StringBuilder();
        for (String key : params.keySet()) {

            Object value = params.get(key);


            if (value == null) continue;
            if (value instanceof List) {

                /*StringBuilder stringBuilder = new StringBuilder();

                List invalues = (List) value;
                for (int i = 0; i < invalues.size(); i++) {
                    stringBuilder.append("?");
                    stringBuilder.append(",");
                }

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                sql = sql.replaceAll("\\s*:\\s*" + key.trim() + "\\b", stringBuilder.toString());*/
                sql = sql.replaceAll("\\s*:\\s*" + key.trim() + "\\b", "?");
            } else if (value.getClass().isArray()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?");
                Object[] arr = (Object[]) value;

                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == null) {
                        continue;
                    } else {
                        sql = sql.replaceAll("\\s*:\\s*" + key.trim() + i + "\\b", stringBuilder.toString());
                    }
                }
            } else {
                if ("groupby".equals(key)) {
                    sql = sql.replaceAll("\\s*:\\s*" + key + "\\b", " "+ value.toString() + " ");
                } else if ("orderby".equals(key)) {
                    sql = sql.replaceAll("\\s*:\\s*" + key + "\\b", " "+ value.toString() + " ");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("?");
                    sql = sql.replaceAll("\\s*:\\s*" + key.trim() + "\\b",  " " + stringBuilder.toString() + " ");
                }
            }


        }
        return sql;
    }

    public static String queryReplace(String sqlid, LinkedHashMap<String, Object> params) throws Exception {
        String sqlFromCache = SqlKit.sql(sqlid);
//        System.out.println("原始query-SQL:"+sqlFromCache);
        String sql = processNull(sqlFromCache, params);
        String parseSQL = parseSQLForIn(sql, params);
//        System.out.println("过滤query-SQL:"+parseSQL);
        return parseSQL;
    }


    public static String executeInsert(String sqlid, LinkedHashMap<String, Object> params) throws Exception {

        String sqlFromCache = SqlKit.sql(sqlid);
        String e = processNullForInsert(sqlFromCache, params);
        String parseSQL = parseSQLForIn(e, params);
        parseSQL = parseSQL.replaceAll(":[a-z|A-Z|_|0-9]+", " null ");
//        System.out.println("insert=" + parseSQL);
        return parseSQL;
    }

    public static String executeUpdate(String sqlid, LinkedHashMap<String, Object> params) throws Exception {
        String sqlFromCache = SqlKit.sql(sqlid);
        if (null != params && !params.isEmpty() && params.size() > 0) {
            String e = processNullForUpdate(sqlFromCache, params);
            String parseSQL = parseSQLForIn(e, params);
            return parseSQL;
        } else {
            return sqlFromCache;
        }

    }


    public static String executeDelete(String sqlid, LinkedHashMap<String, Object> params) throws Exception {
        String sqlFromCache = SqlKit.sql(sqlid);
        String e = processNullForDelete(sqlFromCache, params);
        String parseSQL = parseSQLForIn(e, params);
//        System.out.println("delete=" + parseSQL);
        return parseSQL;
    }


    public static String findListForPaging(String sqlid, LinkedHashMap<String, Object> params) throws Exception {
        String sqlFromCache = SqlKit.sql(sqlid);
//        System.out.println("原始pageQuery-SQL:"+sqlFromCache);
        String sql = processNull(sqlFromCache, params);
        String parseSQL = parseSQLForIn(sql, params);
//        parseSQL.concat(" limit " + (page.getCount() - 1) * page.getPageSize()).concat(", ").concat(page.getPageSize()+"");
//        System.out.println("过滤pageQuery-SQL:"+parseSQL);
        return parseSQL;
    }

    public static void insert() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        //houseid,:younger,:children,:older,:rooms,:area,:plan_code,:remark,
        params.put("houseid1", null);
        params.put("younger", "younger");
        params.put("children", null);
        params.put("older", null);
        params.put("rooms", null);
        params.put("area", null);
        params.put("plan_code", null);
        params.put("remark", null);
        try {
            String insert = SqlOperator.executeInsert("hello.insert", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("id", null);
        params.put("status", "2");
        params.put("name", null);
        String delete = null;
        try {
            delete = SqlOperator.executeDelete("hello.delete", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(delete);
    }


    public static void update() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("status", null);
        params.put("name", 123);
        params.put("id", "2");
        params.put("plan_code", null);
        params.put("customer_id", null);
        String update = null;
        try {
            update = SqlOperator.executeUpdate("hello.update", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("替换后："+update);
    }

    public static String query(String sqlname, LinkedHashMap<String, Object> params) {
        String querySql = null;
        try {
            querySql = SqlOperator.queryReplace(sqlname, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return querySql;
    }


    public static void main(String[] args) {
       /* String str = "select a.id,a.house_id,a.user_id,a.name,a.sex,a.tel,a.datetime,a.adviser_id,a.layout_id,a.evaluate,a.source,a.type,c.nickname,d.`name` adviser_name,a.arrival_type\n" +
                "        from t_preorder a left join customer b on a.user_id=b.id\n" +
                "        left join customer_ext c on c.customerid=b.id\n" +
                "        left join t_dd d on a.adviser_id=d.id\n" +
                "        where a.house_id=:house_id\n" +
                "        and a.datetime >= DATE_FORMAT(:start_datetime, '%Y-%m-%d') and a.datetime &lt; DATE_FORMAT(:end_endtime, '%Y-%m-%d')\n" +
                "        and type=:type  and a.adviser_id in(:adviser_id)\n" +
                "        and(b.loginid like :loginid or c.nickname like :nickname or c.realname like :realname) " ;
//               + "        order by a.datetime asc";
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap();
        String startDatetime = DateUtils.getCurrentTime(); //开始时间
        String endDatetime = DateUtils.dateParseString(DateUtils.addDays(DateUtils.getCurrentDateTime(), 3));


        hashMap.put("house_id", 102);
        hashMap.put("start_datetime", startDatetime);
        hashMap.put("end_endtime", endDatetime);
        hashMap.put("type", null);
        hashMap.put("adviser_id", null);
        hashMap.put("loginid", "%null%");
        hashMap.put("nickname", "%null%");
        hashMap.put("realname", "%null%");
        String sysSql = processNull(str, hashMap);
        System.out.println(sysSql);

        String sql = "select * from tal where a=sdf and parent_pageid=:345parent_pageid345 and a_123b=:a_123b123543 and click_sortno=:click_sortno";
        *//* String tempSql = sql.replaceAll(":[a-z|A-Z|_|0-9]+", " null ");
        System.out.println("tempSql>"+tempSql);
        System.out.println(sql);*/
        String tempSql = "\n" +
                "        select date_format(a.execute_time, '%m月%d') execute_time,count(1) count from timed_job_detail a\n" +
                "        left join timed_job b on b.id = a.job_id " +
                "where status=1 and a=:a and b=:b and c=:c " +
                "group by date_format(a.execute_time, '%Y-%m-%d')" +
                "  order by orderby=:orderby   ";
        tempSql = tempSql.replaceAll("where\\s*group", " group ");
        tempSql = tempSql.replaceAll("where\\s*order", " order ");
        String temp1 = tempSql.replaceAll("where\\s*$", "  ");
//        System.out.println(temp1);

        /*String updateSql = "update table set like_count=:like_count where id=:id and like_count=:count";
        LinkedHashMap updateMap = new LinkedHashMap();
        updateMap.put("like_count", "1");
        updateMap.put("id", "2");
        updateMap.put("count", "3");
        String update = processNullForUpdate(updateSql, updateMap);
        String parseSQL = parseSQLForIn(update, updateMap);
        System.out.println(parseSQL);*/

        String newSql = "select c.name,c.mobile,c.customer_id,c.customer_level,c.purpose,e.name\n" +
                "        adviser,c.customer_type,crc.regist_channel,\n" +
                "        date_format(d.created,'%m.%d %H:%i') created,f.sex,c.is_visit,c.is_call,tc.type cooperation_type,tc.name cooperation_name,coo.code cooperation_remark\n" +
                "        from e_customer c left join e_customer_regist_channel crc on c.mobile=crc.mobile and crc.status=1\n" +
                "        left join e_customer_adviser_relation d on d.customer_id=c.customer_id <!--and d.status=1-->\n" +
                "        left join e_customer e on e.customer_id = d.current_adviser_id\n" +
                "        left join e_customer_follow_log b on c.customer_id = b.customer_id and b.status=1\n" +
                "        left join customer_ext f on f.customerid = c.customer_id\n" +
                "        left join t_cooperation_shop coo on coo.`code`=crc.regist_channel and coo.status=1\n" +
                "        left join t_cooperation tc on tc.id=coo.cooperation_id and tc.status=1\n" +
                "        where c.status=1 and c.project_id=:project_id and c.group_type=0\n" +
                "        and c.customer_type in (:customer_type) and d.current_adviser_id in (:current_adviser_id)\n" +
                "        and d.created>=:created0" +
                "        and d.created&lt;=:created1" +
                "        and (c.name like :keyvalue0 or c.mobile like :keyvalue1 or e.name like :keyvalue2)\n" +
                "        and c.is_call=:is_call\n" +
                "        and c.is_visit=:is_visit\n" +
                "        group by :groupby"+
                "        order by :orderby"
                ;
        LinkedHashMap map = new LinkedHashMap();
        map.put("project_id", 1);
        map.put("customer_type", null);
        map.put("current_adviser_id", null);
        map.put("created0", null);
        map.put("created1", null);
        map.put("keyvalue0", 31);
        map.put("keyvalue1", 4);
        map.put("keyvalue2", null);
        map.put("is_call", null);
        map.put("is_visit", null);
        map.put("groupby", "name123");
        map.put("orderby", "name asc,id desc");
//        map.put("orderby", null);

        String sql = processNull(newSql, map);

        String parseSQL = parseSQLForIn(sql, map);
        System.out.println("过滤pageQuery-SQL:"+parseSQL);
    }
}
