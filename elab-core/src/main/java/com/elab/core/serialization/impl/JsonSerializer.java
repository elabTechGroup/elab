package com.elab.core.serialization.impl;

import com.alibaba.fastjson.JSON;
import com.elab.core.serialization.SerializeBase;

/**
 * @author liuhx on 2016/12/30 23:12
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class JsonSerializer extends SerializeBase {
    @Override
    public String ToSerializedString(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T FromSerializedString(String str, Class<T> classType) {
        T obj =  JSON.parseObject(str, classType);
        try {
            if(null != obj){
                return obj;
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        return  null;
    }


    private static JsonSerializer instance = new JsonSerializer();

    public static JsonSerializer getInstance(){
        return  instance;
    }
}