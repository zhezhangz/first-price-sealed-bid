package org.example.auction.domain;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Auction {

    private String id;

    private String product;

    /**
     * The minimum price of the auction, in cents.
     */
    @Positive(message = "minPrice must be positive")
    private Long minPrice;

    private String seller;

    private AuctionStatus status;

    private List<Bid> bids;

    public Auction(String product, Long minPrice, String seller) {
        this.product = product;
        this.minPrice = minPrice;
        this.seller = seller;
    }

    public void open() {
        this.setStatus(AuctionStatus.OPEN);
    }

    public void close() {
        this.setStatus(AuctionStatus.CLOSED);
    }

    public void placeBid(Bid bid) {
        if (bid.getPrice() < this.minPrice) {
            throw new BidRejected("Bid price is less than the minimum price");
        }
        if (this.status != AuctionStatus.OPEN) {
            throw new BidRejected("Auction is closed");
        }
        if (bid.getBuyer().equals(this.seller)) {
            throw new BidRejected("Seller cannot bid on their own auction");
        }
    }

    public Optional<Bid> findWinner() {
        return Optional.ofNullable(this.bids)
                .stream().flatMap(Collection::stream)
                .max(Comparator.naturalOrder());
    }
}
