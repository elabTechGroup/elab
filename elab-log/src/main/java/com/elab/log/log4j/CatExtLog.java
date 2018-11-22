package com.elab.log.log4j;

import com.dianping.cat.Cat;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * CAT集成Log4j拓展
 *
 * @author Liukx
 * @create 2018-02-06 17:58
 * @email liukx@elab-plus.com
 **/
public class CatExtLog extends AppenderSkeleton {

    private final int lineLength = 200;


    protected void append(LoggingEvent event) {
        boolean isTraceMode = Cat.getManager().isTraceMode();
        Level level = event.getLevel();
        if (level.isGreaterOrEqual(Level.ERROR)) {
            this.logError(event);
        } else if (!isTraceMode) {
            this.logTrace(event);
        }

    }

    private String buildExceptionStack(Throwable exception) {
        if (exception != null) {
            StringWriter writer = new StringWriter(2048);
            exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        } else {
            return "";
        }
    }

    public void close() {
    }

    private void logError(LoggingEvent event) {
        ThrowableInformation info = event.getThrowableInformation();
        if (info != null) {
            Throwable exception = info.getThrowable();
            Object message = event.getMessage();
            if (message != null) {
                Cat.logError(String.valueOf(message), exception);
            } else {
                Cat.logError(exception);
            }
        }

    }

    private void logTrace(LoggingEvent event) {
        String type = "Log4j";
        String name = event.getLevel().toString();
        Object message = event.getMessage();
        String data;
        if (message instanceof Throwable) {
            data = this.buildExceptionStack((Throwable) message);
        } else {
            data = event.getMessage().toString();
        }

        ThrowableInformation info = event.getThrowableInformation();
        if (info != null) {
            data = data + '\n' + this.buildExceptionStack(info.getThrowable());
            Cat.logError(data, info.getThrowable());
            return;
        }
        Cat.logEvent(type, name, "0", formatShowData(data));
    }

    /**
     * 根据打印出来的内容进行格式化,按照长度进行换行
     *
     * @param msg
     * @return
     */
    public String formatShowData(String msg) {
        int length = msg.length();

        if (length < lineLength) {
            return msg;
        }

        int maxSplit = length / lineLength;
        int mo = length % lineLength;
        if (length % lineLength > 0) {
            maxSplit++;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maxSplit; i++) {
            int index = i * lineLength;
            if (maxSplit == (i + 1)) {
                sb.append(msg.substring(index, index + mo) + "\n");
            } else {
                sb.append(msg.substring(index, index + lineLength) + "\n");
            }
        }
        return sb.toString();
    }

    public boolean requiresLayout() {
        return false;
    }


}
