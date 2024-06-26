package org.example.auction.adapters.sql;

import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionMapperTest {

    @Test
    void should_map_auction_from_domain_model() {
        var domainModel = new Auction("1", "p-1", 1001L, "u-1", AuctionStatus.OPEN, null);

        var persistModel = AuctionMapper.MAPPER.fromDomain(domainModel);

        assertThat(persistModel.getId()).isEqualTo(domainModel.getId());
        assertThat(persistModel.getProduct()).isEqualTo(domainModel.getProduct());
        assertThat(persistModel.getMinPrice()).isEqualTo(domainModel.getMinPrice());
        assertThat(persistModel.getSeller()).isEqualTo(domainModel.getSeller());
        assertThat(persistModel.getStatus()).isEqualTo(domainModel.getStatus().name());
    }

    @Test
    void should_map_auction_to_domain_model() {
        var persistModel = new AuctionPersistModel("1", "p-1", 1001L, "u-1", "OPEN", null);

        var domainModel = AuctionMapper.MAPPER.toDomain(persistModel);

        assertThat(domainModel.getId()).isEqualTo(persistModel.getId());
        assertThat(domainModel.getProduct()).isEqualTo(persistModel.getProduct());
        assertThat(domainModel.getMinPrice()).isEqualTo(persistModel.getMinPrice());
        assertThat(domainModel.getSeller()).isEqualTo(persistModel.getSeller());
        assertThat(domainModel.getStatus().name()).isEqualTo(persistModel.getStatus());
        assertThat(domainModel.getBids()).isNull();
    }

    @Test
    void should_map_auction_to_domain_model_with_bids() {
        var bidPersistModel = new BidPersistModel("bid-1", "1", "u-1", 1001L, Instant.now().toString());
        var persistModel = new AuctionPersistModel("1", "p-1", 1001L, "u-1", "OPEN", List.of(bidPersistModel));

        var domainModel = AuctionMapper.MAPPER.toDomainWithBids(persistModel);

        assertThat(domainModel.getId()).isEqualTo(persistModel.getId());
        assertThat(domainModel.getProduct()).isEqualTo(persistModel.getProduct());
        assertThat(domainModel.getMinPrice()).isEqualTo(persistModel.getMinPrice());
        assertThat(domainModel.getSeller()).isEqualTo(persistModel.getSeller());
        assertThat(domainModel.getStatus().name()).isEqualTo(persistModel.getStatus());
        assertThat(domainModel.getBids()).hasSize(1);
    }
}
