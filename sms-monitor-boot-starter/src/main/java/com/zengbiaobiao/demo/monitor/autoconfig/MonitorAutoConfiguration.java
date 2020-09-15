package com.zengbiaobiao.demo.monitor.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengsam
 */
@Configuration
@ConditionalOnClass(MonitorService.class)
@EnableConfigurationProperties(MonitorProperties.class)
public class MonitorAutoConfiguration {
    @Autowired
    private MonitorProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public MonitorService monitorService() {
        return new MonitorService(properties);
    }
}
