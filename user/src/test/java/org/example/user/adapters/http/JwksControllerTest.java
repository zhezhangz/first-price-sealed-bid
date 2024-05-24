package org.example.user.adapters.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JwksController.class)
class JwksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_get_jwks() throws Exception {
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
