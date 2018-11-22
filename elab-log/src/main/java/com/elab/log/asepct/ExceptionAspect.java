//package com.elab.log.asepct;
//
//import com.dianping.cat.Cat;
//import com.elab.core.CoreConstant;
//import com.elab.core.aop.annotations.ExceptionHandle;
//import com.elab.core.bean.BaseInfo;
//import com.elab.core.bean.Info;
//import com.elab.core.collections.NameValueCollection;
//import com.elab.core.configuration.ConfigManager;
//import com.elab.core.exception.CoreException;
//import com.elab.core.log.LogEntry;
//import com.elab.core.log.LogLevel;
//import com.elab.core.serialization.SerializeFactory;
//import com.elab.core.utils.*;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//
///**
// * @author liuhx on 2017/1/5 17:49
// * @version V1.0
// * @email liuhx@elab-plus.com
// */
//public class ExceptionAspect implements MethodInterceptor {
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        String name = "类名：" + invocation.getThis().getClass().getName() + " | 方法名：" + invocation.getMethod().getName();
//        ExceptionHandle handle = invocation.getMethod().getAnnotation(ExceptionHandle.class);
//        boolean isThrowException = false;
//        String moduleName = null;
//        Exception outterEx = null;
//        LogEntry entry = new LogEntry(invocation.getThis().getClass().getName());
//        entry.setSystemName(ConfigManager.getLogManager().systemName);
//        if (handle != null) {
//            isThrowException = handle.Throwable();
//            moduleName = handle.ModuleName().getName();
//        } else {
//            moduleName = invocation.getThis().getClass().getName();
//        }
//        entry.setModuleName(moduleName);
//        NameValueCollection props = new NameValueCollection();
//        props.put("方法：", name);
//        String customerid = "";
//        String inParams = "";
//        if(null != invocation.getArguments()) {
//            String tokenStr = WebUtils.getRequest().getHeader(CoreConstant.ACCESS_TOKEN);
//            String decodeValue = DESUtils.decode(CoreConstant.AUTH_TOKEN_KEY, tokenStr);
//            try {
//                if(StringUtils.isNotEmpty(decodeValue) && decodeValue.contains("|")) {
//                    String[] temp = decodeValue.split("\\|");
//                    if(temp.length == 2 && !StringUtils.isEmpty(temp[1])) {
//                        customerid = temp[1];
//                    }
//                }
//            } catch (Exception e) {
//                customerid = "";
//            }
//            inParams = SerializeFactory.getJsonSerializer().ToSerializedString(invocation.getArguments());
//            if (invocation.getThis().getClass().getPackage().getName().indexOf("controller") >= 0) {
//                Cat.getProducer().logEvent("controller-request", inParams);
//            }
//            if (ObjectUtils.isNotEmpty(customerid)) {
//                customerid = "\"customerid\":"+"\""+customerid+"\",";
//                inParams.replaceFirst("\\{", "{".concat(customerid));
//            }
//            props.put("入参", inParams);
//        }
//
//        Object result = null;
//        try {
//            result = invocation.proceed();
//            if (invocation.getThis().getClass().getPackage().getName().indexOf("controller") >= 0) {
//                String response = SerializeFactory.getJsonSerializer().ToSerializedString(result);
//                Cat.getProducer().logEvent("controller-response", response);
//            }
//            props.put("出参", result);
//
//            entry.logLevel = LogLevel.Info;
//            entry.setProperties(props);
//            LogUtils.send(entry);
//            return result;
//        } catch (CoreException bizEx) {
//            props.put("业务异常：", bizEx.getMessage());
//            if(isThrowException) {
//                outterEx = bizEx;
//            }
//            result = SerializeFactory.getJsonSerializer().ToSerializedString(new BaseInfo(false, bizEx.getMessage(), bizEx.getMessage()));
//            entry.logLevel = LogLevel.Info;
//            entry.setProperties(props);
//            LogUtils.send(entry);
//            if (invocation.getThis().getClass().getPackage().getName().indexOf("controller") >= 0) {
//                Cat.getProducer().logError("exception", bizEx);
//            }
////            return result;
//            return new Info(false, "error.0000", bizEx.getMessage());
//        } catch (Exception innerEx) {
//            props.put("系统异常：", ExceptionUtils.getExceptionInfo(innerEx));
//            entry.logLevel = LogLevel.Error;
//            if(isThrowException) {
//                outterEx = innerEx;
//            }
////            result = SerializeFactory.getJsonSerializer().ToSerializedString(new BaseInfo(false,"error.0000", "系统异常"));
//            entry.setProperties(props);
//            LogUtils.send(entry);
//            if (invocation.getThis().getClass().getPackage().getName().indexOf("controller") >= 0) {
//                Cat.getProducer().logError("exception", innerEx);
//            }
//            return new Info(false, "error.0000", "系统异常");
//        }
//
//    }
//}
