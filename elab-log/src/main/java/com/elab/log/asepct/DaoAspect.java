package com.elab.log.asepct;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.elab.core.utils.ObjectUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import java.util.List;

/**
 * @author liuhx on 2017/5/10 15:28
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class DaoAspect {
    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
    private final Logger logger = Logger.getLogger(CatAspect.class);

    public void before(JoinPoint point) {
        Transaction transaction = null;
        try {
            Class clazz = point.getTarget().getClass();
            String method = point.getSignature().getName();
            String params = ObjectUtils.objectParseJsonStr(point.getArgs());
            transaction = Cat.getProducer().newTransaction("SQL.Method", clazz.getName().concat(".").concat(method));
            logger.debug(" 参数值 : " + params);
        } finally {
            tranLocal.set(transaction);
        }
    }

    public void after(JoinPoint point, Object retValue) {
        Transaction transaction = tranLocal.get();
        if (null != transaction) {
            try {
                StringBuffer params = new StringBuffer();
                if (retValue instanceof List) {
                    List retValueList = (List) retValue;
                    params.append(" list 大小 : " + retValueList.size());
                } else {
                    params.append(ObjectUtils.objectParseJsonStr(retValue));
                }
//                Cat.getProducer().logEvent("Dao.Response", params);
                logger.debug(" 返回值 : " + params);
                transaction.setStatus(Transaction.SUCCESS);
            } catch (Exception e) {
                transaction.setStatus(e);
            } finally {
//                tranLocal.remove();
                transaction.complete();
            }
        }
    }
}
