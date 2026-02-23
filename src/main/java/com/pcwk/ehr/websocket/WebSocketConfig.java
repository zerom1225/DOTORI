package com.pcwk.ehr.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Controller
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    EchoHandler echoHandler;
	

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // /chatRoom 경로로 WebSocket 핸들러를 매핑
        registry.addHandler(echoHandler, "/chatRoom")
                .setAllowedOrigins("http://localhost:8080");  // 로컬 도메인에서 접근 허용
    }
    

}
