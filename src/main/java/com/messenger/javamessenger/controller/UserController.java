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
 * Kontroler REST do operacji użytkownika: rejestracja, logowanie, lista użytkowników online.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final PrincipalUtils principalUtils;

    /**
     * Tworzy kontroler użytkownika.
     * @param userService serwis użytkownika
     * @param authenticationManager menedżer uwierzytelniania
     * @param principalUtils narzędzie pomocnicze do uzyskiwania informacji o zalogowanym użytkowniku
     */
    public UserController(UserService userService, AuthenticationManager authenticationManager, PrincipalUtils principalUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.principalUtils = principalUtils;
    }

    /**
     * Rejestruje nowego użytkownika.
     * Endpoint dostępny publicznie, przyjmujący dane użytkownika i zwracający zarejestrowany obiekt.
     *
     * @param userDTO dane rejestracyjne (login, hasło)
     * @return obiekt UserEntity nowo zarejestrowanego użytkownika
     */
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public UserEntity register(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    /**
     * Zwraca dane aktualnie zalogowanego użytkownika.
     *
     * @return encja użytkownika
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated")
    public UserEntity me() {
        return principalUtils.getCurrentUserEntity();
    }

    /**
     * Loguje użytkownika i zapisuje kontekst bezpieczeństwa.
     * Endpoint przyjmujący dane logowania, wykonujący uwierzytelnienie oraz zapis kontekstu w sesji HTTP.
     *
     * @param userDTO dane logowania (login, hasło)
     * @param request obiekt żądania HTTP
     * @param response obiekt odpowiedzi HTTP
     * @return obiekt UserEntity zalogowanego użytkownika
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
     * Zwraca listę użytkowników (bez aktualnego użytkownika).
     * Endpoint tylko dla uwierzytelnionych użytkowników.
     *
     * @param auth obiekt uwierzytelnienia zawierający login aktualnego użytkownika
     * @return lista UserDTO reprezentujących użytkowników online
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
