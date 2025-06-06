package com.messenger.javamessenger.controller;

import java.util.UUID;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.security.UuidAwareUserDetails;
import com.messenger.javamessenger.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public UUID register(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO).getId();
    }

    @PostMapping("/login")
    public UUID login(@RequestBody UserDTO userDTO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(userDTO.getLogin(), userDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authRequest);
        var principal = (UuidAwareUserDetails) authentication.getPrincipal();

        SecurityContext context = contextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        contextHolderStrategy.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        return principal.getUuid();
    }
}
