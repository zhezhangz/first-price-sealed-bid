package org.example.auction;

import org.example.auction.domain.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AuctionApplicationTests {

    @MockBean
    private AuctionRepository auctionRepository;

    @Test
    void contextLoads() {
    }

}
