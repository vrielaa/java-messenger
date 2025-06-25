
package com.messenger.javamessenger.controller;

import com.messenger.javamessenger.service.MessageService;
import com.messenger.javamessenger.util.PrincipalUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = true)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private PrincipalUtils principalUtils;

    @Test
    @WithMockUser
    void shouldReturnOkForGetMessages() throws Exception {
        String otherUserId = "123e4567-e89b-12d3-a456-426614174000"; // przykładowy UUID
        mockMvc.perform(get("/api/v1/message/" + otherUserId))
                .andExpect(status().isOk());
    }

    @Test

    void shouldReturnUnauthorizedForGetMessagesWithoutAuth() throws Exception {
        String otherUserId = "123e4567-e89b-12d3-a456-426614174000"; // przykładowy UUID
        mockMvc.perform(get("/api/v1/message/" + otherUserId))
                .andExpect(status().isUnauthorized());
    }



//    @Test
//    @WithMockUser(roles = "USER") // Dodaj odpowiednią rolę, jeśli jest wymagana
//    void shouldReturnOkForSendMessage() throws Exception {
//        String json = """
//    {
//      "receiver": "123e4567-e89b-12d3-a456-426614174000",
//      "publicKeyIdToEncryptedContentMap": {"key1": "val1"}
//    }
//    """;
//        mockMvc.perform(post("/api/v1/message")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void shouldReturnUnauthorizedForSendMessageWithoutAuth() throws Exception {
//        String json = """
//        {
//          "receiver": "123e4567-e89b-12d3-a456-426614174000",
//          "publicKeyIdToEncryptedContentMap": {"key1": "val1"}
//        }
//        """;
//        mockMvc.perform(post("/api/v1/message")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isUnauthorized());
//    }



}