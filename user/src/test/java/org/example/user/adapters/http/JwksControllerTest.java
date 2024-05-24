package org.example.user.adapters.http;

import org.example.user.adapters.jwt.JwtTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JwksController.class)
class JwksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenGenerator jwtTokenGenerator;

    @Test
    void should_get_jwks() throws Exception {
        doReturn("test-key").when(jwtTokenGenerator).n();
        doReturn("AQAB").when(jwtTokenGenerator).e();

        mockMvc.perform(get("/.well-known/jwks.json"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                          "keys": [
                            {
                              "kty": "RSA",
                              "e": "AQAB",
                              "use": "sig",
                              "alg": "RS256",
                              "kid": "test-key",
                              "n": "test-key"
                            }
                          ]
                        }"""
                ));
    }
}
