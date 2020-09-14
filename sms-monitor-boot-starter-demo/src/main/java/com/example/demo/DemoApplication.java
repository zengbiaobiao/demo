package com.example.demo;

import com.schindler.ioee.sms.monitor.autoconfig.SmsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Consumer;

/**
 * @author zengsam
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private SmsMonitorService monitorService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        monitorService.subscribe("abc", data -> System.out.println("receive data:" + data));
    }
}
