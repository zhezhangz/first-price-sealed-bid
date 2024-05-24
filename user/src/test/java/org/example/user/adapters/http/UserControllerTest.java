package org.example.user.adapters.http;

import org.example.user.domain.InvalidUser;
import org.example.user.domain.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void should_get_200_when_service_recognise_user() throws Exception {
        doReturn("mock-response").when(userService).login("test-user");

        mockMvc.perform(put("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test-user\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("mock-response"));
    }

    @Test
    void should_get_404_when_service_doesnt_recognise_user() throws Exception {
        doThrow(InvalidUser.class).when(userService).login(anyString());

        mockMvc.perform(put("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test-user\"}"))
                .andExpect(status().isNotFound());
    }
}
