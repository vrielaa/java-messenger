package com.messenger.javamessenger.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Konfiguracja bezpieczeństwa aplikacji Messenger.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Konfiguruje łańcuch filtrów bezpieczeństwa.
     *
     * @param http obiekt HttpSecurity do konfiguracji zabezpieczeń
     * @return skonfigurowany SecurityFilterChain
     * @throws Exception w przypadku błędu konfiguracji
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("Set-Cookie"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            /**
                             * Obsługuje wylogowanie - usuwa ciasteczko sesji i zwraca kod 200 OK.
                             *
                             * @param request obiekt HttpServletRequest
                             * @param response obiekt HttpServletResponse
                             * @param authentication obiekt Authentication reprezentujący uwierzytelnionego użytkownika
                             */
                            Cookie emptySessionIdCookie = new Cookie("JSESSIONID", null);
                            emptySessionIdCookie.setMaxAge(0);
                            emptySessionIdCookie.setHttpOnly(true);
                            emptySessionIdCookie.setPath("/");
                            response.addCookie(emptySessionIdCookie);
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .permitAll());

        return http.build();
    }

    /**
     * Konfiguruje menedżera uwierzytelniania.
     *
     * @param userDetailsService serwis do zarządzania danymi użytkowników
     * @param passwordEncoder kodujący hasło użytkownika
     * @return skonfigurowany AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    /**
     * Tworzy instancję kodera hasła.
     *
     * @return koder hasła BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
