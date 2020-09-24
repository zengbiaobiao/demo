package com.zengbiaobiao.socketiodemo.server;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zengsam
 */
@Configuration
@EnableConfigurationProperties({SocketServerConfig.WsConfig.class})
@Slf4j
public class SocketServerConfig {


    @Autowired
    private WsConfig wsConfig;


    @Bean
    public SocketIOServer server() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(wsConfig.getHost());
        config.setPort(wsConfig.getPort());
        // should set to true so that more than client can connect successfully for each IP.
        config.setRandomSession(true);
        SocketIOServer server = new SocketIOServer(config);
        return server;
    }


    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }

    @ConfigurationProperties("wss.server")
    @Data
    public class WsConfig {
        private String host;
        private int port;
    }


}