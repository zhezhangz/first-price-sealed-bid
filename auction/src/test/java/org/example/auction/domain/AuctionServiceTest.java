package org.example.auction.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidRepository bidRepository;

    @BeforeEach
    void setUp() {
        auctionService = new AuctionService(auctionRepository, bidRepository);
    }

    @Test
    void should_create_auction_to_repository() {
        doReturn(null).when(auctionRepository).save(any());

        final Auction auction = new Auction("p-1", 1001L, "u-mock");
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
        Auction mockAuction = new Auction("1", "p-mock", 1001L, "u-mock", AuctionStatus.OPEN, null);
        doReturn(mockAuction).when(auctionRepository).save(any());

        final Auction saved = auctionService.create(new Auction());

        assertThat(saved).isEqualTo(mockAuction);
    }

    @Test
    void should_verify_bid_placement_and_save() {
        Auction mockAuction = new Auction("1", "p-mock", 1001L, "u-seller", AuctionStatus.OPEN, null);
        doReturn(Optional.of(mockAuction)).when(auctionRepository).findById(any());
        doReturn(new Bid("1", "1", "u-mock", 10_000L, Instant.now())).when(bidRepository).save(any());

        final Bid saved = auctionService.placeBid(new Bid(null, "1", "u-buyer", 10_000L, null));

        assertThat(saved.getId()).isEqualTo("1");

        final ArgumentCaptor<Bid> bidArgumentCaptor = ArgumentCaptor.forClass(Bid.class);
        verify(bidRepository, times(1)).save(bidArgumentCaptor.capture());
        final Bid bidToBeSaved = bidArgumentCaptor.getValue();
        assertThat(bidToBeSaved.getBidAt()).isNotNull();
    }
}
