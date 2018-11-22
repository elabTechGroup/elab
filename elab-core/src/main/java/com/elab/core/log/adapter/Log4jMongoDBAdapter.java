package com.elab.core.log.adapter;

import com.elab.core.configuration.ConfigManager;
import com.elab.core.configuration.entity.LogConfig;
import com.elab.core.log.ILogAdapter;
import com.elab.core.log.LogEntry;
import com.elab.core.log.LogLevel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 记录于数据库形式的日志
 * @author liuhx on 2017/1/2 14:52
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class Log4jMongoDBAdapter implements ILogAdapter {

    private static Logger instance = null;
    private static Boolean isLogDebug = false;
    private static Boolean isLogInfo = false;

    public static Logger getLogger(){
        if(null == instance){
            synchronized(Log4jMongoDBAdapter.class){
                LogConfig config = ConfigManager.getLogManager().getEmitterByName("log4jMongoDBAdapter");
                if(null == instance){
                    instance = Logger.getLogger("SM");
                    isLogDebug = config.logDebug;
                    isLogInfo = config.logInfo;
                    if(null == config){
                        BasicConfigurator.configure();
                    }else{
                        HashMap<String,String> properties = config.getProperties();
                        if(null == properties || properties.isEmpty()){
                            BasicConfigurator.configure();
                        }else{
                            Properties prop = new Properties();
                            Iterator<Map.Entry<String,String>> iter = properties.entrySet().iterator();
                            while (iter.hasNext()) {
                                Map.Entry<String,String> entry = iter.next();
                                prop.setProperty(entry.getKey(), entry.getValue());
                            }
                            PropertyConfigurator.configure(prop);
                        }
                    }
                }
            }
        }
        return instance;
    }

    public void log(LogEntry logEntry) throws Exception {

        Logger logger = Log4jMongoDBAdapter.getLogger();
        if (ConfigManager.getLogManager().getIsDebugEnabled() && isLogDebug && logEntry.logLevel == LogLevel.Debug) {
            logger.debug(logEntry);
        }

        if (isLogInfo && logEntry.logLevel == LogLevel.Info) {
            logger.info(logEntry);
        }

        if(logEntry.logLevel == LogLevel.Error) {
            logger.error(logEntry);
        }
    }
}
