package com.elab.core.serialization.impl;

import com.elab.core.serialization.SerializeBase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author liuhx on 2016/12/30 23:11
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class XmlSerializer extends SerializeBase {
    @Override
    public String ToSerializedString(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xm头声明信息
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }

    @Override
    public <T> T FromSerializedString(String str, Class<T> classType) {
        try {
            JAXBContext context = JAXBContext.newInstance(classType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static XmlSerializer instance = new XmlSerializer();

    public static XmlSerializer getInstance(){
        return  instance;
    }
}