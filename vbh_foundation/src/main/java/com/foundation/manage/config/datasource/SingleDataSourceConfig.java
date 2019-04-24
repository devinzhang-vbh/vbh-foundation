
package com.foundation.manage.config.datasource;

import cn.stylefeng.roses.core.datascope.DataScopeInterceptor;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 多数据源配置
 *
 * @author yt
 */
@Configuration
@ConditionalOnProperty(prefix = "guns.muti-datasource", name = "open", havingValue = "false", matchIfMissing = true)
@EnableTransactionManagement
@MapperScan(basePackages = {"com.foundation.manage.*.mapper"})
public class SingleDataSourceConfig {

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}

