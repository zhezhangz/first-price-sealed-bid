package org.example.auction.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.catchException;

class AuctionTest {

    @Test
    void should_close_auction() {
        Auction auction = Auction.builder().status(AuctionStatus.OPEN).build();

        auction.close();

        assertThat(auction.getStatus()).isEqualTo(AuctionStatus.CLOSED);
    }

    @Test
    void should_open_auction() {
        Auction auction = Auction.builder().status(AuctionStatus.CLOSED).build();

        auction.open();

        assertThat(auction.getStatus()).isEqualTo(AuctionStatus.OPEN);
    }

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

    @Nested
    class WhenGettingWinnerOfAuction {

        @Test
        void should_return_empty_when_there_is_no_bid() {
            Auction auction = Auction.builder().bids(null).build();

            final Optional<Bid> winner = auction.findWinner();

            assertThat(winner).isEmpty();
        }

        @Test
        void should_return_the_only_bid_when_there_is_only_one() {
            var bids = List.of(
                    Bid.builder().price(1_000L).buyer("user-1").bidAt(Instant.now()).build()
            );
            Auction auction = Auction.builder().bids(bids).build();

            final Optional<Bid> winner = auction.findWinner();

            assertThat(winner).hasValue(bids.get(0));
        }

        @Test
        void should_return_bid_with_the_highest_price() {
            var bids = List.of(
                    Bid.builder().price(1_000L).buyer("user-1").bidAt(Instant.now()).build(),
                    Bid.builder().price(1_200L).buyer("user-2").bidAt(Instant.now()).build(),
                    Bid.builder().price(1_100L).buyer("user-3").bidAt(Instant.now()).build()
            );
            Auction auction = Auction.builder().bids(bids).build();

            final Optional<Bid> winner = auction.findWinner();

            assertThat(winner).hasValue(bids.get(1));
        }

        @Test
        void should_return_the_first_bid_with_the_highest_price() {
            var bids = List.of(
                    Bid.builder().price(1_000L).buyer("user-1").bidAt(Instant.parse("2021-01-01T00:00:00Z")).build(),
                    Bid.builder().price(1_200L).buyer("user-2").bidAt(Instant.parse("2021-01-02T00:00:00Z")).build(),
                    Bid.builder().price(1_200L).buyer("user-3").bidAt(Instant.parse("2021-01-01T00:00:00Z")).build(),
                    Bid.builder().price(1_100L).buyer("user-4").bidAt(Instant.parse("2021-01-03T00:00:00Z")).build()
            );
            Auction auction = Auction.builder().bids(bids).build();

            final Optional<Bid> winner = auction.findWinner();

            assertThat(winner).hasValue(bids.get(2));
        }
    }
}
