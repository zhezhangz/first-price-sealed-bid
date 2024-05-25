package org.example.auction.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class BidTest {

    @Test
    void should_bid_with_higher_price_be_greater() {
        Bid bid1 = Bid.builder().price(100L).bidAt(Instant.now()).build();
        Bid bid2 = Bid.builder().price(200L).bidAt(Instant.now()).build();

        assertThat(bid2).isGreaterThan(bid1);
    }

    @Test
    void should_bid_with_earlier_bid_at_time_be_greater_when_prices_are_the_same() {
        Bid bid1 = Bid.builder().price(100L).bidAt(Instant.now().minusSeconds(10)).build();
        Bid bid2 = Bid.builder().price(100L).bidAt(Instant.now()).build();

        assertThat(bid1).isGreaterThan(bid2);
    }
}
