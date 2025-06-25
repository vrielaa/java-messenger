package com.messenger.javamessenger.controller;



import com.messenger.javamessenger.controller.PublicKeyController;
import com.messenger.javamessenger.model.PublicKeyEntity;
import com.messenger.javamessenger.service.PublicKeyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(PublicKeyController.class)
class PublicKeyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicKeyService publicKeyService;

    @Test
    @WithMockUser
    void shouldReturnOkForGetPublicKeys() throws Exception {
        UUID userId = UUID.randomUUID();
        when(publicKeyService.getAllForUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/public-key/" + userId))
                .andExpect(status().isOk());

        verify(publicKeyService, times(1)).getAllForUserId(userId);
    }
}