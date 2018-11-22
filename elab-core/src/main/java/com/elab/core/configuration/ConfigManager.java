package com.elab.core.configuration;

import com.elab.core.CoreConstant;
import com.elab.core.configuration.entity.GlobalConfig;
import com.elab.core.configuration.entity.LogManager;
import com.elab.core.configuration.entity.SettingConfig;
import com.elab.core.serialization.SerializeFactory;
import com.elab.core.utils.StringUtils;

/**
 * @author liuhx on 2017/1/2 13:43
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class ConfigManager {
    static{
        try {
            globalConfig = SerializeFactory.getXmlSerializer().FromFile(CoreConstant.GLOABLE_CONFIG, GlobalConfig.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            globalConfig = new GlobalConfig();
        }
    }

    private static GlobalConfig globalConfig;

    public static LogManager getLogManager() {  return globalConfig.getLogManager();  }

    public static String getProperty(String name) {
        if(StringUtils.isEmpty(name)) {
            return "";
        }
        SettingConfig config = globalConfig.getSettings().getPropertyByName(name);
        if(null == config) {
            return "";
        }
        return config.getValue();
    }
}
