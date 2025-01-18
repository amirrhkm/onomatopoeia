package com.example.websocket;

import org.glassfish.tyrus.server.Server;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 9999, "/", new HashMap<>(), GameServer.class);

        try {
            server.start();
            System.out.println("WebSocket server started on ws://localhost:9999/game");
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
