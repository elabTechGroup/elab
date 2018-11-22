package com.elab.core.log;

/**
 * @author liuhx on 2017/1/2 14:41
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public interface ILogAdapter {
    void log(LogEntry logEntry) throws Exception;
}
