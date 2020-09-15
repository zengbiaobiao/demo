package com.example.demo;

import com.zengbiaobiao.demo.monitor.autoconfig.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zengsam
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private MonitorService monitorService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        monitorService.subscribe("abc", data -> System.out.println("receive data:" + data));
    }
}
