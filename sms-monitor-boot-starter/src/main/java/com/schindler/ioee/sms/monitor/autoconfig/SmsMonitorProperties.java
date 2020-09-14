package com.schindler.ioee.sms.monitor.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zengsam
 */
@ConfigurationProperties(prefix = "sms.monitor")
public class SmsMonitorProperties {
    private String loginUrl;
    private String username;
    private String password;
    private String serverUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
