
package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.dto.UserDTO;
import com.messenger.javamessenger.model.UserEntity;
import com.messenger.javamessenger.service.UserService;
import com.messenger.javamessenger.util.PrincipalUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

@MockBean
private UserService userService;

@MockBean
private AuthenticationManager authenticationManager;

@MockBean
private PrincipalUtils principalUtils;

    @Test
    @WithMockUser(username = "testuser")
    void shouldReturnOkForGetOnlineUsers() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setLogin("otheruser");
        when(userService.getOnlineUsersExcept("testuser")).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/user/online"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getOnlineUsersExcept("testuser");
    }
}