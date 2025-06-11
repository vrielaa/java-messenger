package com.messenger.javamessenger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @class WebSocketConfig
 * @brief Klasa konfiguracyjna WebSocket, implementująca WebSocketMessageBrokerConfigurer.
 *
 * Umożliwia skonfigurowanie brokera wiadomości, punktów STOMP oraz obsługi SockJS.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * @brief Konfiguruje brokera wiadomości.
     *
     * Włącza prosty broker w pamięci, który obsługuje wiadomości przesyłane na prefiksy "/topic".
     * Ustawia prefiks "/app" jako punkt wyjściowy dla wiadomości wychodzących od klienta do serwera.
     *
     * @param registry Obiekt MessageBrokerRegistry służący do konfiguracji brokera wiadomości.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); ///< Włączenie brokera dla ścieżek subskrypcji.
        registry.setApplicationDestinationPrefixes("/app"); ///< Prefiks dla wiadomości klient -> serwer.
    }

    /**
     * @brief Rejestruje punkty końcowe STOMP do połączeń WebSocket.
     *
     * Dodaje endpoint "/ws", który może być używany przez klientów do nawiązywania połączeń WebSocket.
     * Włącza także obsługę SockJS jako fallback dla przeglądarek bez wsparcia WebSocket.
     *
     * @param registry Obiekt StompEndpointRegistry używany do rejestracji endpointów WebSocket.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") ///< Główny endpoint STOMP.
                .setAllowedOrigins("*") ///< Zezwolenie na połączenia z dowolnych domen (uwaga produkcja!).
                .withSockJS(); ///< Włączenie SockJS jako fallback.
    }
}