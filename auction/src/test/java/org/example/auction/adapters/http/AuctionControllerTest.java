package org.example.auction.adapters.http;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class WhenCreatingAuction {

        @Test
        void should_reject_anonymous_creation() throws Exception {
            mockMvc.perform(post("/auctions")
                            .with(SecurityMockMvcRequestPostProcessors.anonymous())
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"product\": \"auction1\", \"minPrice\": 1000}"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void should_return_success_when_post_auction() throws Exception {
            mockMvc.perform(post("/auctions")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"product\": \"auction1\", \"minPrice\": 1000}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("success"));
        }
    }
}
