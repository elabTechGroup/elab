package com.elab.core.serialization;

/**
 * @author liuhx on 2016/12/30 23:13
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public enum SerializeType {
    XML(1),
    JSON(2)
    ;
    private final int type;
    SerializeType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
}
