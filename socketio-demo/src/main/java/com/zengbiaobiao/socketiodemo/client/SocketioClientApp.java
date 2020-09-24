package com.zengbiaobiao.socketiodemo.client;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;

import java.net.URI;

/**
 * @author zengsam
 */
public class SocketioClientApp {

    private static final String HOST = "http://127.0.0.1";

    public static void main(String[] args) throws InterruptedException {
        Socket socket = connect();
        System.out.println("sleep for 5 seconds and then close socket io.");
        Thread.sleep(5000);
        socket.close();
        Thread.sleep(5000);
    }

    private static Socket connect() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            opts.timeout = 5000;
            Manager manager = new Manager(new URI(HOST), opts);
            Socket socket = manager.socket("/car");
            socket.on(Socket.EVENT_CONNECT, objects -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println(socket.id() + " connected. ");
            }).on("message", data -> {
                System.out.println(data[1]);
            }).on(Socket.EVENT_DISCONNECT, objects -> System.out.println(socket.id() + " disconnected")
            ).on(Socket.EVENT_ERROR, objects -> System.out.println(objects.toString()));
            socket.connect();
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
