package com.schindler.ioee.sms.monitor.autoconfig;

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
@ConditionalOnClass(SmsMonitorService.class)
@EnableConfigurationProperties(SmsMonitorProperties.class)
public class SmsMonitorAutoConfiguration {
    @Autowired
    private SmsMonitorProperties properties;
    @Bean
    @ConditionalOnMissingBean
    public SmsMonitorService monitorService() {
        return new SmsMonitorService(properties);
    }
}
