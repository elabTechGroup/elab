package com.elab.core.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author liuhx on 2017/1/2 15:07
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class FreeMarkerUtils {
    /**
     * 使用freemarker模版替换技术
     *
     * @param params
     *            , 参数集合, params中可以包含对象及多级对象
     * @param content
     *            , 要替换的文本
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String renderModel(Map<String, Object> params,
                                     String content) throws IOException, TemplateException {
        StringTemplateLoader loader = new StringTemplateLoader(); // 模版加载器
        loader.putTemplate("chain", content);
        Configuration config = new Configuration();
        config.setTemplateLoader(loader);
        Template template = config.getTemplate("chain", "UTF-8");
        StringWriter out = new StringWriter();

        template.process(params, out);
        return out.toString();
    }
}
