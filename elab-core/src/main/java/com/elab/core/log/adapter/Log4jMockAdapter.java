package com.elab.core.log.adapter;

import com.elab.core.log.ILogAdapter;
import com.elab.core.log.LogEntry;
import com.elab.core.serialization.SerializeFactory;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 直接打印控制台形式的日志
 * @author liuhx on 2017/1/2 14:54
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class Log4jMockAdapter implements ILogAdapter {

    public void log(LogEntry logEntry) throws Exception{
        String message = SerializeFactory.getJsonSerializer().ToSerializedString(logEntry);
        message = StringEscapeUtils.unescapeJava(message);
        System.out.println(message);
        /*Transaction t = Cat.getProducer().newTransaction("core", "params");
        t.addData(message);
        t.complete();*/
    }
}
