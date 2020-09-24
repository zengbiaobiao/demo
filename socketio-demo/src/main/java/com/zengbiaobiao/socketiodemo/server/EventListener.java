package com.zengbiaobiao.socketiodemo.server;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zengsam
 */
@Slf4j
@Component
public class EventListener {


    /****
     * When client disconnect, it should leave the room
     * @param client
     */
    @OnDisconnect
    public void onDisconnectEvent(SocketIOClient client) {
        if (client != null) {

            log.info("[EVENT] disconnected, client={}, sessionId={}.", client.getRemoteAddress(), client.getSessionId());
            client.disconnect(); // Is it necessary?
        } else {
            log.error("[EVENT] Client is null!");
        }
    }

    /*****
     * Handle monitor request
     * @param client
     */
    @OnConnect
    public void onConnectEvent(SocketIOClient client) {
        for (int i = 0; i < 15; i++) {
            String message = new Date().toString();
            client.sendEvent("message", message);
            log.info("send message: {}", message);

        }
    }


}
