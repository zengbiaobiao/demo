package com.zengbiaobiao.socketiodemo.server;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

/**
 * @author zengsam
 */
@SpringBootApplication
@Slf4j
public class SocketioServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SocketioServerApplication.class, args);
    }

    private final SocketIOServer server;


    @Autowired
    EventListener eventListener;


    @Autowired
    public SocketioServerApplication(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("start socket io server....");
            addNamespace(this.server);
            server.start();
            log.info("start socket io server successfully!");
        } catch (Exception e) {
            log.error("start socket io server error: {}", e.toString());
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("stop socket io server....");
        server.stop();
        log.info("stop socket io server successfully!");
    }

    /*****
     * Add namespace and listener here.
     * add namespace before socket server started can avoid reference circle for socket server and listener.
     * @param server
     */
    private void addNamespace(SocketIOServer server) {
        SocketIONamespace eventNamespace = server.addNamespace("/car");
        eventNamespace.addListeners(eventListener);
    }
}
