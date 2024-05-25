package org.example.auction.adapters.sql;

import org.example.auction.domain.Bid;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class BidMapperTest {

    @Test
    void should_map_to_domain_model() {
        final BidPersistModel persistModel = new BidPersistModel("id-1", "auction-1", "buyer-1", 100L, "2021-01-01T00:00:00Z");

        final Bid bid = BidMapper.MAPPER.toDomain(persistModel);

        assertThat(bid.getId()).isEqualTo("id-1");
        assertThat(bid.getAuctionId()).isEqualTo("auction-1");
        assertThat(bid.getBuyer()).isEqualTo("buyer-1");
        assertThat(bid.getPrice()).isEqualTo(100L);
        assertThat(bid.getBidAt()).isEqualTo("2021-01-01T00:00:00Z");
    }

    @Test
    void should_map_from_domain_model() {
        final Bid bid = Bid.builder()
                .id("id-1")
                .auctionId("auction-1")
                .buyer("buyer-1")
                .price(100L)
                .bidAt(Instant.parse("2021-01-01T00:00:00Z"))
                .build();

        final BidPersistModel persistModel = BidMapper.MAPPER.fromDomain(bid);

        assertThat(persistModel.getId()).isEqualTo("id-1");
        assertThat(persistModel.getAuctionId()).isEqualTo("auction-1");
        assertThat(persistModel.getBuyer()).isEqualTo("buyer-1");
        assertThat(persistModel.getPrice()).isEqualTo(100L);
        assertThat(persistModel.getBidAt()).isEqualTo("2021-01-01T00:00:00Z");
    }
}
