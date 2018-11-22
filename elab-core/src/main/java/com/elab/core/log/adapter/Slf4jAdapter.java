package com.elab.core.log.adapter;

import com.elab.core.configuration.ConfigManager;
import com.elab.core.configuration.entity.LogConfig;
import com.elab.core.log.ILogAdapter;
import com.elab.core.log.LogEntry;
import com.elab.core.log.LogLevel;
import com.elab.core.serialization.SerializeFactory;
import com.elab.core.utils.ObjectUtils;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author liuhx on 2017/3/23 18:38
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class Slf4jAdapter implements ILogAdapter {

    private static Logger instance = null;
    private static Boolean isLogDebug = false;
    private static Boolean isLogInfo = false;

    public static Logger getLogger() {
        if(null == instance){
            synchronized(Slf4jAdapter.class) {
                LogConfig config = ConfigManager.getLogManager().getEmitterByName("slf4jAdapter");
                if(null == instance) {
                    instance = LoggerFactory.getLogger("elab");
                    isLogDebug = config.logDebug;
                    isLogInfo = config.logInfo;
                    HashMap<String,String> properties = config.getProperties();
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
        return instance;
    }

    @Override
    public void log(LogEntry logEntry) throws Exception {
        if (ObjectUtils.isNotEmpty(logEntry.getFormat()) || (ObjectUtils.isNotEmpty(logEntry.getContents()) && logEntry.getContents().length > 0)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(logEntry.getFormat(), logEntry.getContents());
            logEntry.setContent(ft.getMessage());
            logEntry.contents = null;
            logEntry.format = null;
        }
        String message = SerializeFactory.getJsonSerializer().ToSerializedString(logEntry);
        message = message.replaceAll("\\\\t", "");
        message = message.replaceAll("\\\\n", "");
        message = message.replaceAll("\\\\", "");
        Logger logger = Slf4jAdapter.getLogger();


        if (ConfigManager.getLogManager().getIsDebugEnabled() && isLogDebug && logEntry.logLevel == LogLevel.Debug) {
            logger.debug(message);
        }

        if (isLogInfo && logEntry.logLevel == LogLevel.Info) {
            logger.info(message);
        }

        if(logEntry.logLevel == LogLevel.Error) {
            logger.error(message);
        }
    }
}
