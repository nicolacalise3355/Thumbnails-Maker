package com.calise.tb_backend.handlers;

import com.calise.tb_backend.services.sockets.SocketSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class FileUploadWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private SocketSessionsService socketSessionsService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        socketSessionsService.addSession(session, session.getId());
        session.sendMessage(new TextMessage(session.getId()));
        System.out.println("WebSocket Connection Established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("[MSG]: " + message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketSessionsService.removeSession(session.getId());
        System.out.println("WebSocket Connection Closed");
    }

    public void sendUploadStatus(String sessionId, String status, int code) throws Exception {
        WebSocketSession session = socketSessionsService.getSession(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage("{\"code\": "+code+", \"status\": \"" + status + "\"}"));
        }
    }

    public void closeConnection(String sessionId) throws Exception {
        WebSocketSession session = socketSessionsService.getSession(sessionId);
        session.close();
    }


    public WebSocketSession getSession(String sessionId) {
        return socketSessionsService.getSession(sessionId);
    }
}
