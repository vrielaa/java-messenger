package com.messenger.javamessenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Konfiguracja bezpieczeństwa WebSocket, rozszerzająca AbstractSecurityWebSocketMessageBrokerConfigurer.
 * Umożliwia wyłączenie zabezpieczeń pochodzenia (same origin) dla połączeń WebSocket.
 */
@Configuration
public class WebSocketSecurityConfigurer extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    /**
     * Wyłącza zabezpieczenia pochodzenia (same origin) dla połączeń WebSocket.
     *
     * @return true, aby wyłączyć zabezpieczenia pochodzenia
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
