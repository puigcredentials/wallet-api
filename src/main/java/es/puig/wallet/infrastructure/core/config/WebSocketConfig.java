package es.puig.wallet.infrastructure.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping webSocketMapping(PinRequestWebSocketHandler webSocketHandler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/api/v1/pin", webSocketHandler);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(-1);
        return mapping;
    }
}
