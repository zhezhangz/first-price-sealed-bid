package org.example.auction.domain;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    public void open() {
        this.setStatus(AuctionStatus.OPEN);
    }
}
