package org.example.auction.adapters.sql;

import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionRepository;
import org.example.auction.domain.AuctionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuctionRepositoryImplTest {

    @Autowired
    private JpaAuctionRepository jpaAuctionRepository;

    private AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        auctionRepository = new AuctionRepositoryImpl(jpaAuctionRepository);
    }

    @Test
    void should_save_new_auction_and_create_id() {
        final Auction auction = new Auction(null, "p-1", 1001L, "u-1", AuctionStatus.OPEN);

        final Auction saved = auctionRepository.save(auction);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getProduct()).isEqualTo("p-1");
        assertThat(saved.getMinPrice()).isEqualTo(1001L);
    }
}
