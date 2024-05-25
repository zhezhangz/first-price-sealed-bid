package org.example.auction.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.catchException;

class AuctionTest {

    @Nested
    class WhenPlacingBid {

        private Auction auction = Auction.builder().minPrice(100L).status(AuctionStatus.OPEN).seller("user-1").build();

        @ParameterizedTest
        @ValueSource(longs = {0, 1, 99})
        void should_reject_when_bid_price_is_less_than_min_price(long bidPrice) {
            Bid bid = Bid.builder().price(bidPrice).buyer("user-2").build();

            final Exception exception = catchException(() -> auction.placeBid(bid));

            assertThat(exception).isInstanceOf(BidRejected.class)
                    .hasMessageEndingWith("Bid price is less than the minimum price");
        }

        @Test
        void should_reject_when_auction_is_closed() {
            auction.setStatus(AuctionStatus.CLOSED);
            Bid bid = Bid.builder().price(1_000L).buyer("user-2").build();

            final Exception exception = catchException(() -> auction.placeBid(bid));

            assertThat(exception).isInstanceOf(BidRejected.class)
                    .hasMessageEndingWith("Auction is closed");
        }

        @Test
        void should_reject_when_buyer_is_seller_themselves() {
            Bid bid = Bid.builder().price(1_000L).buyer("user-1").build();

            final Exception exception = catchException(() -> auction.placeBid(bid));

            assertThat(exception).isInstanceOf(BidRejected.class)
                    .hasMessageEndingWith("Seller cannot bid on their own auction");
        }

        @ParameterizedTest
        @ValueSource(longs = {100, 101, 1_000_000})
        void should_accept_when_bid_price_is_greater_than_or_equal_to_min_price(long bidPrice) {
            Bid bid = Bid.builder().price(bidPrice).buyer("user-2").build();

            assertThatNoException().isThrownBy(() -> auction.placeBid(bid));
        }
    }
}
