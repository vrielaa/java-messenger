package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UserDetailsWithEntity;
import com.messenger.javamessenger.service.UserService;
import com.messenger.javamessenger.util.PrincipalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @class UserController
 * @brief Kontroler REST do operacji użytkownika: rejestracja, logowanie, lista użytkowników online.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService; ///< Serwis do obsługi operacji użytkownika.
    private final AuthenticationManager authenticationManager; ///< Menedżer uwierzytelniania Spring Security.
    private final SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy(); ///< Strategia trzymania kontekstu bezpieczeństwa.
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final PrincipalUtils principalUtils; ///< Repozytorium kontekstu bezpieczeństwa w sesji.

    /**
     * @brief Konstruktor kontrolera użytkownika.
     * @param userService Serwis użytkownika.
     * @param authenticationManager Menedżer uwierzytelniania.
     */
    public UserController(UserService userService, AuthenticationManager authenticationManager, PrincipalUtils principalUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.principalUtils = principalUtils;
    }

    /**
     * @brief Rejestruje nowego użytkownika.
     *
     * Endpoint dostępny publicznie, przyjmujący dane użytkownika i zwracający zarejestrowany obiekt.
     *
     * @param userDTO Dane rejestracyjne (login, hasło).
     * @return Obiekt UserEntity nowo zarejestrowanego użytkownika.
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public UserEntity register(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated")
    public UserEntity me() {
        return principalUtils.getCurrentUserEntity();
    }

    /**
     * @brief Loguje użytkownika i zapisuje kontekst bezpieczeństwa.
     *
     * Endpoint przyjmujący dane logowania, wykonujący uwierzytelnienie oraz zapis kontekstu w sesji HTTP.
     *
     * @param userDTO Dane logowania (login, hasło).
     * @param request Obiekt żądania HTTP.
     * @param response Obiekt odpowiedzi HTTP.
     * @return Obiekt UserEntity zalogowanego użytkownika.
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public UserEntity login(@RequestBody UserDTO userDTO,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                userDTO.getLogin(),
                userDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authRequest);
        var principal = (UserDetailsWithEntity) authentication.getPrincipal();

        SecurityContext context = contextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        contextHolderStrategy.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        return principal.getUser();
    }

    /**
     * @brief Zwraca listę użytkowników (bez aktualnego użytkownika).
     *
     * Endpoint tylko dla uwierzytelnionych użytkowników.
     *
     * @param auth Obiekt uwierzytelnienia zawierający login aktualnego użytkownika.
     * @return Lista UserDTO reprezentujących użytkowników online.
     */
    @GetMapping("/online")
    @PreAuthorize("isAuthenticated()")
    public List<UserDTO> getUsers(Authentication auth) {
        String currentLogin = auth.getName();
        return userService.getUsersExcept(currentLogin).stream()
                .map(user -> new UserDTO(user.getId(), user.getLogin()))
                .collect(Collectors.toList());
    }
}
