package com.elab.core.serialization;

/**
 * @author liuhx on 2016/12/30 23:09
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public interface ISerializer {
    <T> Boolean ToFile(T obj, String fileName);

    String ToSerializedString(Object obj);

    <T> T FromSerializedString(String str,Class<T> objType);

    <T> T FromFile(String filePath, Class<T> objType);
}
