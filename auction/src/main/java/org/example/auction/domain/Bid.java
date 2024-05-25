package org.example.auction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bid implements Comparable<Bid> {

    private String id;

    private String auctionId;

    private String buyer;

    private Long price;

    private Instant bidAt;

    @Override
    public int compareTo(Bid that) {
        if (this.price.equals(that.price)) {
            return that.bidAt.compareTo(this.bidAt);
        } else {
            return this.price.compareTo(that.price);
        }
    }
}
