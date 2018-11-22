package com.elab.log.message;

import com.dianping.cat.message.Event;
import com.dianping.cat.message.internal.DefaultMessageProducer;

/**
 * 重写CAT消息生产的模型,将CAT的异常做特殊处理,与指定的开发人员进行绑定
 *
 * @author Liukx
 * @create 2018-02-08 18:00
 * @email liukx@elab-plus.com
 **/
public class CatMessageProducer extends DefaultMessageProducer {


    @Override
    public void logEvent(String type, String name, String status, String nameValuePairs) {
        String author = "";
        if ("ERROR".equals(status)) {
            int startIndex = nameValuePairs.indexOf("[");
            int endIndex = nameValuePairs.indexOf("]");
            if (startIndex >= 0 && endIndex > 0) {
                author = nameValuePairs.substring(startIndex, endIndex + 1) + " - ";
            }
        }
        Event event = newEvent(type, author + name);

        if (nameValuePairs != null && nameValuePairs.length() > 0) {
            event.addData(nameValuePairs);
        }

        event.setStatus(status);
        event.complete();
    }
}
