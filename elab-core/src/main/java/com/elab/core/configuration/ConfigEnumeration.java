package com.elab.core.configuration;

import com.elab.core.CoreConstant;
import com.elab.core.configuration.entity.EnumerationMode;
import com.elab.core.configuration.entity.EnumerationModes;
import com.elab.core.configuration.entity.ValueMode;
import com.elab.core.serialization.SerializeFactory;
import com.elab.core.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典数据配置，一个key对应集合，用于和后台管理系统的字典枚举类型相呼应
 * 枚举配置化，主要用于文字写死之类的
 * @author liuhx on 2017/3/22 16:53
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class ConfigEnumeration {

    public static Map<String,List<ValueMode>> enumerationMap;
    public static Map<String,ValueMode> valueModeMap;

    static{
        //枚举
        enumerationMap = new HashMap();
        valueModeMap = new HashMap();

        EnumerationModes modes = SerializeFactory.getXmlSerializer().FromFile(CoreConstant.ENUMERATION_CONFIG, EnumerationModes.class);
        List<EnumerationMode> list =  modes.getEnumeration();
        if(list != null){
            for(EnumerationMode mode : list){
                String type = mode.getType();
                List<ValueMode> values = mode.getValues();
                enumerationMap.put(type,values);
                for(ValueMode vm : values){
                    valueModeMap.put(type+vm.getVal(),vm);
                }
            }
        }
    }

    public static String getName(String type,String code){
        ValueMode vm = valueModeMap.get(type + code);
        if(vm != null) {
            return vm.getName();
        }
        return "";
    }

    public static boolean isOutOfRange(String type, String value){
        if(!StringUtils.areNotEmpty(type,value)){
            return true;
        }

        List<ValueMode> vm  = ConfigEnumeration.enumerationMap.get(type);

        if(null == vm || vm.isEmpty()){
            return true;
        }

        for(ValueMode item: vm){
            if(item.getVal().equals(value)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        List<ValueMode> valueModeList = enumerationMap.get("10001");
        for(ValueMode vm : valueModeList){
            System.out.println(vm.getName()+":"+vm.getVal());
        }

        String name = getName("10001", "1");
        System.out.println(name);
    }
}
