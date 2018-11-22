package com.elab.core.utils;

import com.elab.core.log.ILogAdapter;
import com.elab.core.log.LogEntry;
import com.elab.core.log.LogLevel;
import com.elab.core.log.LogProvider;
import com.elab.core.log.adapter.Log4jFileAdapter;
import com.elab.core.serialization.SerializeFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuhx on 2017/1/2 14:40
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class LogUtils {
    private static final ExecutorService service = Executors.newCachedThreadPool();
    private static ILogAdapter logger1 = null;
    private static ILogAdapter logger2 = null;


    public LogUtils() {
    }

    private static ILogAdapter getLogger() throws Exception{
        if(null == logger1){
            synchronized(Log4jFileAdapter.class){
                if(null == logger1){
                    return LogProvider.createDefault();
                }
            }
        }
        return logger1;
    }

    private static ILogAdapter getLogger(String logType) throws Exception{
        if(null == logger2){
            synchronized(Log4jFileAdapter.class){
                if(null == logger2){
                    return LogProvider.create(logType);
                }
            }
        }
        return logger2;
    }



    public static void send(final String model,final String message) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger().log(new LogEntry(model, message));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final String logType, final String model,final String message) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger(logType).log(new LogEntry(model, message));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }


    public static void send(final String model,final String message, final LogLevel logLevel) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger().log(new LogEntry(model, message, logLevel));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final String logType, final String model,final String message, final LogLevel logLevel) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger(logType).log(new LogEntry(model, message, logLevel));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final String model,final Exception ex) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger().log(new LogEntry(model, SerializeFactory.getJsonSerializer().ToSerializedString(ex), LogLevel.Error));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final String logType,final String model,final Exception ex) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger(logType).log(new LogEntry(model, SerializeFactory.getJsonSerializer().ToSerializedString(ex), LogLevel.Error));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final String logType,final LogEntry entry) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger(logType).log(entry);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }

    public static void send(final LogEntry entry) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger().log(entry);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }



    public static void send(final Class classes, String format, final String... message) {
        try {
            service.submit(new Runnable() {
                public void run() {
                    try {
                        getLogger().log(new LogEntry(classes.getName(), format, message));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LogUtils.service.shutdownNow();
                }
            });
        }
    }
}
