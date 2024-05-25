package org.example.auction;

import org.example.auction.domain.BidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AuctionApplicationTests {

    @Test
    void contextLoads() {
    }

    @MockBean
    private BidRepository bidRepository;
}
