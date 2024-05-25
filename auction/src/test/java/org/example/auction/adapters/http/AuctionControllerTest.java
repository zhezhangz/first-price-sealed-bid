package org.example.auction.adapters.http;

import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionService auctionService;

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
            verifyNoInteractions(auctionService);
        }

        @Test
        void should_return_success_when_post_auction() throws Exception {
            doReturn(new Auction("1", "mock-p", 1001L, "mock-user")).when(auctionService).create(any());

            mockMvc.perform(post("/auctions")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"product\": \"auction1\", \"minPrice\": 1000}"))
                    .andExpect(status().isCreated())
                    .andExpect(content().json("""
                            {
                                "id": "1",
                                "product": "mock-p",
                                "minPrice": 1001,
                                "seller": "mock-user"
                            }
                            """));

            verify(auctionService).create(new Auction(null, "auction1", 1000L, "user"));
        }
    }
}
