package com.elab.core;

/**
 * @author liuhx on 2017/1/2 13:57
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class CoreConstant {
    public static final String DELIMITER=",";
    public static final String SIGN_TYPE = "sign_type";
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String FORMAT = "format";
    public static final String METHOD = "method";
    public static final String TIMESTAMP = "timestamp";
    public static final String VERSION = "version";
    public static final String SIGN = "sign";
    //token 密钥
    public static final String AUTH_TOKEN_KEY = "MIGfMA0GCSqGSIb3DQEBAQUA";
    public static final String ACCESS_TOKEN = "auth_token";
    public static final String HEAD = "head";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIMEZONE = "GMT+8";
    public static final String CHARSET = "charset";
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";

    public static final String GLOABLE_CONFIG = CoreConstant.class.getClassLoader().getResource("global-config.xml").getPath();
    public static final String ENUMERATION_CONFIG = CoreConstant.class.getClassLoader().getResource("dictionary/enumeration.xml").getPath();

}
