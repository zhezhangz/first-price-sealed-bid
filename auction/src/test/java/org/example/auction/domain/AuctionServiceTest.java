package org.example.auction.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
        doReturn(null).when(auctionRepository).save(any());

        final Auction auction = new Auction(null, "p-1", 1001L, "u-mock", null);
        auctionService.create(auction);

        var auctionCaptor = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository).save(auctionCaptor.capture());
        final Auction toBeSaved = auctionCaptor.getValue();
        assertThat(toBeSaved.getId()).isNull();
        assertThat(toBeSaved.getProduct()).isEqualTo("p-1");
        assertThat(toBeSaved.getMinPrice()).isEqualTo(1001L);
        assertThat(toBeSaved.getSeller()).isEqualTo("u-mock");
        assertThat(toBeSaved.getStatus()).isEqualTo(AuctionStatus.OPEN);
    }

    @Test
    void should_return_auction_created_by_repository() {
        Auction mockAuction = new Auction("1", "p-mock", 1001L, "u-mock", AuctionStatus.OPEN);
        doReturn(mockAuction).when(auctionRepository).save(any());

        final Auction saved = auctionService.create(new Auction());

        assertThat(saved).isEqualTo(mockAuction);
    }
}
