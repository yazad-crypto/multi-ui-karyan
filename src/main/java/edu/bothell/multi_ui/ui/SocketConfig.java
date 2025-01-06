package edu.bothell.multi_ui.ui;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {
    // PROPERTIES --------------------------------------------------------------------
    private final SocketHandle h;

    // CONSTRUCTOR -------------------------------------------------------------------
    public SocketConfig(SocketHandle h){
        this.h = h;
    }

    // METHODS -----------------------------------------------------------------------
    @SuppressWarnings("null")
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(h, "/socket").setAllowedOrigins("*");   
    }
    
}
