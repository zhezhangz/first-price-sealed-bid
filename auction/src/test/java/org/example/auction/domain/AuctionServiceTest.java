package org.example.auction.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        auctionService = new AuctionService(auctionRepository);
    }

    @Test
    void should_create_auction_to_repository() {
        Auction mockSaved = new Auction("1", "p-mock", 1001L, "u-mock");
        doReturn(mockSaved).when(auctionRepository).save(any());

        final Auction saved = auctionService.create(new Auction(null, "p-1", 1001L, "u-mock"));

        assertThat(saved).isEqualTo(mockSaved);
    }
}
