package com.schindler.ioee.sms.monitor.autoconfig;

import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author zengsam
 */
public class SmsMonitorService {
    private SmsMonitorProperties properties;

    public SmsMonitorService(SmsMonitorProperties properties) {
        this.properties = properties;
    }

    public void subscribe(String url, Consumer<String> callback) {
        System.out.println("login to sms security center:");
        System.out.println("loginUrl=" + properties.getLoginUrl());
        System.out.println("username=" + properties.getUsername());
        System.out.println("password=" + properties.getPassword());
        System.out.println("connect to sms monitor:");
        System.out.println("serverUrl=" + properties.getServerUrl());
        System.out.println("receive monitor data");
        callback.accept("current time:" + new Date().toString());
        callback.accept("current time:" + new Date().toString());
        callback.accept("current time:" + new Date().toString());
    }

    public void unsubscribe(String url) {
        System.out.println("unsubscribe:" + url);
    }
}
