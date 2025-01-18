package com.example.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/game")
public class GameServer {
    private static final CopyOnWriteArraySet<Session> clients = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("Player joined. Current players: " + clients.size());
        broadcast("Player joined. Current players: " + clients.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received: " + message);
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Player left. Current players: " + clients.size());
        broadcast("Player left. Current players: " + clients.size());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    private void broadcast(String message) {
        for (Session client : clients) {
            client.getAsyncRemote().sendText(message);
        }
    }
}
