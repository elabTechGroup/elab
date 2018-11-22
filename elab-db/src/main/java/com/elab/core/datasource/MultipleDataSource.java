package com.elab.core.datasource;

import com.elab.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源
 *
 * @author liuhx on 2016/12/7 19:00
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceHelp.getDataSource();
        if (StringUtils.isEmpty(datasource)) {
            datasource = "采用XML中配置的默认数据源";
        }
        logger.debug("current-datasource:" + datasource);
        return datasource;
    }
}
