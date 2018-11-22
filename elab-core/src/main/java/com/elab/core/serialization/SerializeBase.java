package com.elab.core.serialization;

import com.elab.core.utils.FileUtils;
import com.elab.core.utils.StringUtils;

import java.io.IOException;

/**
 * @author liuhx on 2016/12/30 23:10
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public abstract class SerializeBase implements ISerializer {
    public <T> Boolean ToFile(T obj, String fileName) {
        if(StringUtils.isEmpty(fileName)){
            return  false;
        }
        String str = ToSerializedString(obj);
        try {
            FileUtils.writeFile(str.getBytes(), fileName);
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> T FromFile(String filePath, Class<T> classType) {
        if(StringUtils.isEmpty(filePath)){
            return  null;
        }
        try {
            return FromSerializedString(new String(FileUtils.readFile(filePath), "UTF-8"), classType);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public abstract String ToSerializedString(Object obj);

    public abstract <T> T FromSerializedString(String str, Class<T> classType);
}
