package com.elab.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liuhx on 2016/12/11 22:27
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class ObjectUtils {

    private static Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");

    public ObjectUtils() {
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || !StringUtils.isNotEmpty(obj.toString());
    }

    public static Object objIsNull(Object obj) {
        return obj != null && !"null".toUpperCase().equals(obj.toString().toUpperCase())?obj:null;
    }

    public static String parseJsonNode(JSONObject value) {
        if(value == null) {
            return null;
        } else {
            String str = value.toJSONString();
            return StringUtils.isNotEmpty(str) && !"null".equals(str)?str:null;
        }
    }

    public static Double objIsNullByZero(Object obj) {
        if(obj != null && StringUtils.isNotEmpty(obj.toString())) {
            Double value = parseDouble(obj);
            return Double.valueOf(value == null?0.0D:value.doubleValue());
        } else {
            return Double.valueOf(BigDecimal.valueOf(0.0D).setScale(2, 1).doubleValue());
        }
    }

    public static Double objIsNullByZero(JSON obj) {
        if(obj != null && StringUtils.isNotEmpty(obj.toJSONString())) {
            Double value = parseDouble((Object)obj.toJSONString());
            return Double.valueOf(value == null?0.0D:value.doubleValue());
        } else {
            return Double.valueOf(BigDecimal.valueOf(0.0D).setScale(2, 1).doubleValue());
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return obj != null && StringUtils.isNotEmpty(obj.toString());
    }

    public static boolean isNumber(Object value) {
        if(value != null && StringUtils.isNotEmpty(value.toString())) {

            return pattern.matcher(value.toString()).matches();
        } else {
            return false;
        }
    }

    public static Double parseDouble(Object obj) {
        if(obj != null && isNumber(obj)) {
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(obj.toString())).setScale(2, 1);
            return Double.valueOf(bd.doubleValue());
        } else {
            return null;
        }
    }

    public static Double parseDouble(JSON obj) {
        if(obj != null && StringUtils.isNotEmpty(obj.toJSONString())) {
            String text = obj.toJSONString();
            return parseDouble((Object)text);
        } else {
            return null;
        }
    }

    public static Double addition(Object obj1, Object obj2) {
        if(isNumber(obj1) && isNumber(obj2)) {
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(obj1.toString())).add(BigDecimal.valueOf(Double.parseDouble(obj2.toString())));
            return Double.valueOf(bd.setScale(2, 1).doubleValue());
        } else {
            return null;
        }
    }

    public static Double subtract(Object obj1, Object obj2) {
        if(isNumber(obj1) && isNumber(obj2)) {
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(obj1.toString())).subtract(BigDecimal.valueOf(Double.parseDouble(obj2.toString())));
            return Double.valueOf(bd.setScale(2, 1).doubleValue());
        } else {
            return null;
        }
    }

    public static String parseDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(sdf);
        return dateStr;
    }

    public static String parseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(sdf);
        return dateStr;
    }

    public static boolean listIsNull(List list) {
        return list == null || list.size() <= 0;
    }


    public static String jsonNodeParseStr(JSON jsonNode) {
        return jsonNode == null?null:jsonNode.toString();
    }

    public static String objectParseJsonStr(Object object) {
        return object == null?null: JSON.toJSONString(object);
    }

    public static JSONObject strParseJsonNode(String jsonStr) {
            if(StringUtils.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }

        return null;
    }

    /**
     * 空字符串替换成null
     * @param str
     * @return
     */
    public static String strParseNull(String str) {
        if(StringUtils.isNotEmpty(str)) {
            return str;
        }
        return null;
    }

    public static ObjectNode newJsonNode() {
        return (new ObjectMapper()).createObjectNode();
    }

    public static String parseJsonNodeNullStr(JsonNode value) {
        if(value == null) {
            return "";
        } else {
            String v = value.asText();
            return isNotEmpty(v) && !"null".equals(v)?v:"";
        }
    }
}
