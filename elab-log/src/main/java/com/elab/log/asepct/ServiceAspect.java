//package com.elab.log.asepct;
//
//import com.dianping.cat.Cat;
//import com.elab.core.utils.ObjectUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * service切面AOP
// * @author liuhx on 2017/5/16 18:09
// * @version V1.0
// * @email liuhx@elab-plus.com
// */
//public class ServiceAspect {
//
////    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
//
//    public void before(JoinPoint point) {
//        Object target = point.getTarget();
//        String method = point.getSignature().getName();
//        Class classz = target.getClass();
//        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
//                .getMethod().getParameterTypes();
////        Transaction transaction = Cat.getProducer().newTransaction(classz.getSimpleName(), classz.getName().concat("."+method));
//        try {
//            String params = ObjectUtils.objectParseJsonStr(point.getArgs());
////            Cat.getProducer().logEvent(classz.getSimpleName(), classz.getName().concat("."+method));
//            Cat.getProducer().logEvent("Info", classz.getName().concat("."+method));
//            Cat.getProducer().logEvent("Service.Request", params);
//        } catch (Exception e) {
//
//        } finally {
////            tranLocal.set(transaction);
//        }
//    }
//
//    public void afterService(JoinPoint point, Object retValue) {
//
//        String params = ObjectUtils.objectParseJsonStr(retValue);
//        Cat.getProducer().logEvent("Service.Response", params);
//
//        /*Transaction transaction = tranLocal.get();
//        if (null != transaction) {
//            try {
//
//                transaction.setStatus(Transaction.SUCCESS);
//            } catch (Exception e) {
//                transaction.setStatus(e);
//            } finally {
////                tranLocal.remove();
//                transaction.complete();
//            }
//        }*/
//    }
//}
