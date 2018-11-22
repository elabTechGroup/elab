package com.elab.core.log.adapter;

import com.elab.core.collections.NameValueCollection;
import com.elab.core.log.LogEntry;
import com.elab.core.serialization.SerializeFactory;
import com.elab.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author liuhx on 2017/1/2 14:55
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MongoDbPatternLayout extends PatternLayout {
    private static final String PROPERTY_PRE = "P";

    @Override
    public String format(LoggingEvent loggingEvent) {
        Object logMessage = loggingEvent.getMessage();
        if(logMessage == null) {
            return "";
        }
        if(logMessage instanceof LogEntry) {
            LogEntry logEntry = (LogEntry)logMessage;
            DBObject doc = new BasicDBObject();
            if(logMessage != null) {
                if(StringUtils.isNotEmpty(logEntry.getContent())) {
                    doc.put("content", logEntry.getContent().replace("'","|").replace("\"","|").replace("{","").replace("}",""));//URLEncoder.encode(), "utf-8"));
                }
                if(StringUtils.isNotEmpty(logEntry.getLogID())) {
                    doc.put("logid", logEntry.getLogID());
                }
                if(StringUtils.isNotEmpty(logEntry.getLogServerIP())) {
                    doc.put("serverip", logEntry.getLogServerIP());
                }
                if(StringUtils.isNotEmpty(logEntry.getModuleName())) {
                    doc.put("modulename", logEntry.getModuleName());
                }
                if(StringUtils.isNotEmpty(logEntry.getSystemName())) {
                    doc.put("systemname", logEntry.getSystemName());
                }
                if(StringUtils.isNotEmpty(logEntry.getProcessTime())) {
                    doc.put("processtime", logEntry.getProcessTime());
                }
                doc.put("logLevel", logEntry.getLogLevel().toString());
                doc.put("logdate", logEntry.getLogDate());

                if(logEntry.getProperties() != null) {
                    NameValueCollection collection = logEntry.getProperties();
                    int i = 0;
                    for (Object key : collection.keySet()) {
                        doc.put(PROPERTY_PRE + i , key.toString() + collection.get(key).toString());//+ URLEncoder.encode(collection.get(key).toString(), "utf-8") + "】").replace("{","").replace("}","").replace("[","").replace("]",""));
                        i++;
                    }
                }
            }
            return SerializeFactory.getJsonSerializer().ToSerializedString(doc);
        } else {
            return "";
        }
    }
}