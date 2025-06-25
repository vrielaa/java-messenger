package com.messenger.javamessenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Konfiguracja WebSocket, implementująca WebSocketMessageBrokerConfigurer.
 * Pozwala skonfigurować brokera wiadomości, punkty STOMP oraz obsługę SockJS.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Konfiguruje brokera wiadomości.
     * Włącza prosty broker w pamięci, który obsługuje wiadomości przesyłane na prefiksy "/topic/messages".
     *
     * @param registry obiekt MessageBrokerRegistry służący do konfiguracji brokera wiadomości
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/messages");
    }

    /**
     * Rejestruje punkty końcowe STOMP do połączeń WebSocket.
     * Dodaje endpoint "/ws", który może być używany przez klientów do nawiązywania połączeń WebSocket.
     * Włącza także obsługę SockJS jako fallback dla przeglądarek bez wsparcia WebSocket.
     *
     * @param registry obiekt StompEndpointRegistry używany do rejestracji endpointów WebSocket
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }
}