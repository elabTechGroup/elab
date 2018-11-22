package com.elab.core.log;

import com.elab.core.configuration.ConfigManager;
import com.elab.core.exception.CoreException;
import com.elab.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuhx on 2017/1/2 14:41
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class LogProvider {
    public final static Map<String,ILogAdapter> emitters = new HashMap<String, ILogAdapter>();
    private static final ReentrantLock LOG_EMITTER_LOCK = new ReentrantLock();

    public static ILogAdapter createDefault() throws Exception {
        if(null == ConfigManager.getLogManager() || StringUtils.isEmpty(ConfigManager.getLogManager().getDefaultEmitter())){
            throw new CoreException("logger setting is not exist");
        }
        return create(ConfigManager.getLogManager().getDefaultEmitter());
    }

    public static ILogAdapter create(String logType) throws Exception {
        if (StringUtils.isEmpty(logType)) {
            throw new CoreException("logType is not exist");
        }

        ILogAdapter emitter = emitters.get(logType);
        if (null == emitter) {

            try {
                LOG_EMITTER_LOCK.lock();
                if (null == ConfigManager.getLogManager()) {
                    throw new CoreException("logger is not exist");
                }
                emitter = emitters.get(logType);
                if (null == emitter) {
                    emitter = (ILogAdapter) Class.forName(ConfigManager.getLogManager().getEmitterByName(logType).getType()).newInstance();
                    emitters.put(logType, emitter);
                }
            } finally {
                LOG_EMITTER_LOCK.unlock();
            }
        }

        return emitter;
    }

}
