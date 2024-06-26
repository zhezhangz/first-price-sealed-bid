package org.example.auction.adapters.http;

import org.example.auction.adapters.security.AuthorizationService;
import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionNotFound;
import org.example.auction.domain.AuctionService;
import org.example.auction.domain.Bid;
import org.example.auction.domain.BidRejected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionService auctionService;

    @MockBean
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        doReturn(true).when(authorizationService).canAccess(any(), any());
    }

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
            doReturn(Auction.builder().id("1").product("mock-p").minPrice(1001L).seller("mock-user").build())
                    .when(auctionService).create(any());

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
            verify(auctionService).create(new Auction("auction1", 1000L, "user"));
        }

        @Test
        void should_return_error_message_when_min_price_is_not_positive() throws Exception {
            mockMvc.perform(post("/auctions")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"product\": \"auction1\", \"minPrice\": -1}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("{\"minPrice\":\"minPrice must be positive\"}"));
        }
    }

    @Nested
    class WhenListingAuctions {

        @Test
        void should_return_auction_list() throws Exception {
            doReturn(List.of(
                    Auction.builder().id("1").product("mock-p1").minPrice(1001L).seller("mock-user1").build(),
                    Auction.builder().id("2").product("mock-p2").minPrice(1002L).seller("mock-user2").build()
            ))
                    .when(auctionService).findAll(any());

            mockMvc.perform(get("/auctions?page=4&size=8")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(2));
            verify(auctionService).findAll(PageRequest.of(4, 8));
        }
    }

    @Nested
    class WhenCreatingBid {

        @Test
        void should_return_400_when_bid_price_is_not_positive() throws Exception {
            mockMvc.perform(post("/auctions/1/bids")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "price": -1
                                    }"""))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("""
                            {
                                "price": "bid price should be positive"
                            }
                            """));
            verifyNoInteractions(auctionService);
        }

        @Test
        void should_create_bid() throws Exception {
            final Instant mockedBidTime = Instant.parse("2021-09-01T00:00:00Z");
            doReturn(new Bid("1", "a-1", "b-1", 1L, mockedBidTime)).when(auctionService).placeBid(any());
            mockMvc.perform(post("/auctions/1/bids")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "price": 1
                                    }"""))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json("""
                            {
                                "id": "1",
                                "auctionId": "a-1",
                                "buyer": "b-1",
                                "price": 1,
                                "bidAt": "2021-09-01T00:00:00Z"
                            }
                            """));
        }

        @Test
        void should_return_404_when_auction_is_not_found() throws Exception {
            doThrow(new AuctionNotFound()).when(auctionService).placeBid(any());

            mockMvc.perform(post("/auctions/1/bids")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "price": 1
                                    }"""))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                            {
                                "error": "Auction not found"
                            }
                            """));
        }

        @Test
        void should_return_409_when_service_rejects_bid() throws Exception {
            doThrow(new BidRejected("any reason")).when(auctionService).placeBid(any());

            mockMvc.perform(post("/auctions/1/bids")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "price": 1
                                    }"""))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isConflict())
                    .andExpect(content().json("""
                            {
                                "error": "Bid rejected for any reason"
                            }
                            """));
        }
    }

    @Nested
    class WhenTerminatingAuction {

        @Test
        void should_return_404_when_auction_is_not_found() throws Exception {
            doThrow(new AuctionNotFound()).when(auctionService).terminate(any());

            mockMvc.perform(put("/auctions/1/termination")
                            .with(SecurityMockMvcRequestPostProcessors.jwt())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("""
                            {
                                "error": "Auction not found"
                            }
                            """));
        }
    }
}
