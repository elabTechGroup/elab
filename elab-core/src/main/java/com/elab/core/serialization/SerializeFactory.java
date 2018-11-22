package com.elab.core.serialization;

import com.elab.core.serialization.impl.JsonSerializer;
import com.elab.core.serialization.impl.XmlSerializer;

/**
 * @author liuhx on 2016/12/30 23:13
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class SerializeFactory {

    public static ISerializer getXmlSerializer()
    {
        return create(SerializeType.XML);
    }

    public static ISerializer getJsonSerializer()
    {
        return create(SerializeType.JSON);
    }

    private static ISerializer create(SerializeType type){
        ISerializer serializer = null;
        switch (type.getType())
        {
            case 1:
                serializer = XmlSerializer.getInstance();
                break;
            case 2:
                serializer = JsonSerializer.getInstance();
                break;
            default:
                serializer = XmlSerializer.getInstance();
                break;
        }

        return  serializer;
    }
}
